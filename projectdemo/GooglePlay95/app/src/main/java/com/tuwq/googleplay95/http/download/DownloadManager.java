package com.tuwq.googleplay95.http.download;

import android.os.Environment;
import android.os.Handler;
import android.util.SparseArray;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.MyApp;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 负责管理整个项目的下载逻辑
 */
public class DownloadManager {

    //定义下载目录:  /mnt/sdcard/包名/download
    public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
            + File.separator + MyApp.context.getPackageName()
            + File.separator + "download";

    //定义下载状态常量
    public static final int STATE_NONE = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE = 2;//暂停下载
    public static final int STATE_FINISH = 3;//下载完成
    public static final int STATE_ERROR = 4;//下载失败
    public static final int STATE_WAITING = 5;//等待下载，下载任务已经创建，但是run方法还木有执行

    //用来存放所有的监听器对象
    private ArrayList<DownloadObserver> observerList = new ArrayList<>();
    //用来存放所有的DownloadINfo对象,代替Integer為key的hashMap
    private SparseArray<DownloadInfo> downloadInfoMap = new SparseArray<DownloadInfo>();

    //线程池
    ExecutorService executorService = null;
    //借助handler让代码运行到主线程
    Handler handler = new Handler();
    //借组DbUtils进行数据的持久化存储
    DbUtils dbUtils = null;

    private static DownloadManager mInstance = new DownloadManager();
    private DownloadManager(){
        //创建数据库对象
        dbUtils = DbUtils.create(MyApp.context);
        //从db中初始化downloadInfo的数据
        initDownloadInfoFromDB();
        //初始化线程池
        //同时执行线程数量：当前设备的cpu核心数*2 + 1，这样数量的线程能够让cpu效率得到最大发挥
        int threadCount = Runtime.getRuntime().availableProcessors()*2 + 1;
        executorService = Executors.newFixedThreadPool(threadCount);

        //创建文件目录
        File dir = new File(DOWNLOAD_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    /**
     * 从数据库初始化downloadInfo
     */
    private void initDownloadInfoFromDB() {
        try {
            List<DownloadInfo> list = dbUtils.findAll(DownloadInfo.class);
            if(list!=null && list.size()>0){
                //强list中的DownloadINfo取出，放入downloadInfoMap
                for (DownloadInfo info : list){
                    downloadInfoMap.put(info.id,info);
                }
                LogUtil.e("从数据库中初始化了"+list.size()+"数据");
            }else {
                LogUtil.e("数据库中没有DownloadInfo数据");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static DownloadManager create(){
        return mInstance;
    }

    public DownloadInfo getDownloadInfo(AppInfo appInfo){
        return downloadInfoMap.get(appInfo.id);
    }

    /**
     * 下载的方法
     */
    public void download(AppInfo appInfo){
        //1.获取任务对应的DownloadInfo
        DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.id);
        if(downloadInfo==null){
            //创建downloadInfo，并存入到downloadInfoMap
            downloadInfo = DownloadInfo.create(appInfo);
            downloadInfoMap.put(downloadInfo.id,downloadInfo);
            //同时往数据库中存储一份
            saveToDb(downloadInfo);
        }

        //2. 获取任务对应的state
        int state = downloadInfo.state;
        //在这几种状态下才能进行下载：none, pause, error
        if(state==STATE_NONE || state==STATE_PAUSE || state==STATE_ERROR){
            //可以进行下载了,那么就开始创建下载任务，交给线程池执行
            DownloadTask downloadTask = new DownloadTask(downloadInfo);

            //更新任务的state
            downloadInfo.state = STATE_WAITING;
            //通知监听器更新
            notifyDownloadObserver(downloadInfo);

            executorService.execute(downloadTask);
        }

    }

    /**
     * 将DownloadINfo存入db中
     * @param downloadInfo
     */
    private void saveToDb(DownloadInfo downloadInfo) {
        try {
            dbUtils.save(downloadInfo);
            LogUtil.e("将downloadInfo存入db成功！");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载任务类，请求文件数据，保存到本地
     */
    class DownloadTask implements Runnable{
        DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }
        @Override
        public void run() {
            //3.首先将state更改为下载中
            downloadInfo.state = STATE_DOWNLOADING;
            //通知监听器更新
            notifyDownloadObserver(downloadInfo);

            //4.开始下载文件了,a.从头下载   b.断点下载
            HttpStack.HttpResult httpResult = null;
            File file = new File(downloadInfo.path);
            if(!file.exists() || file.length()!=downloadInfo.currentLength){
                file.delete();//删除无效文件
                downloadInfo.currentLength = 0;//重置currentLength

                //请求url
                String url = String.format(Url.Download, downloadInfo.downloadUrl);
                httpResult = HttpStack.getInstance().download(url);
            }else {
                //断点下载
                String url = String.format(Url.BreadDownload, downloadInfo.downloadUrl,
                        downloadInfo.currentLength);
                httpResult = HttpStack.getInstance().download(url);
            }

            //5.读取流，存文件
            if(httpResult!=null && httpResult.getInputStream()!=null){
                //说明文件数据请求成功，
                InputStream is = httpResult.getInputStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    byte[] buffer = new byte[1024*8];//8k的缓冲区
                    int len = -1;
                    while(downloadInfo.state==STATE_DOWNLOADING && (len=is.read(buffer))!=-1){
                        fos.write(buffer,0,len);

                        //通知监听器
                        downloadInfo.currentLength += len;
                        notifyDownloadObserver(downloadInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //下载失败的处理
                    downloadInfo.state = STATE_ERROR;//下载失败
                    notifyDownloadObserver(downloadInfo);
                } finally {
                    //关闭流
                    httpResult.close();
                    try {
                        if(fos!=null)fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //6.代码走到最后的情况，a:下载完成  b.暂停
                    if(file.length()==downloadInfo.size && downloadInfo.state==STATE_DOWNLOADING){
                        //说明是下载完成了
                        downloadInfo.state = STATE_FINISH;
                        notifyDownloadObserver(downloadInfo);
                    }else if(downloadInfo.state==STATE_PAUSE){
                        //通知监听器
                        notifyDownloadObserver(downloadInfo);
                    }

                    //最后更新DB中的数据
                    updateDownloadInfoInDb(downloadInfo);
                }
            }else {
                //说明请求文件数据失败
                downloadInfo.state = STATE_ERROR;//下载失败
                notifyDownloadObserver(downloadInfo);
            }
        }
    }

    /**
     * 更新数据库中的downloadInfo
     */
    private void updateDownloadInfoInDb(DownloadInfo downloadInfo) {
        try {
            dbUtils.update(downloadInfo,"currentLength","state");
            LogUtil.e("更新db中数据成功！");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知所有的监听器下载更新
     * @param downloadInfo
     */
    private void notifyDownloadObserver(final DownloadInfo downloadInfo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (DownloadObserver observer : observerList){
                    observer.onDownloadUpdate(downloadInfo);
                }
            }
        });
    }

    /**
     * 暂停的方法
     */
    public void pause(AppInfo appInfo){
        //找到对应的downloadInfo，将state更改为暂停
        DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.id);
        if(downloadInfo!=null){
            downloadInfo.state = STATE_PAUSE;
        }
    }

    /**
     * 添加一个下载监听器对象
     * @param observer
     */
    public void registerDownloadObserver(DownloadObserver observer){
        if(!observerList.contains(observer)){
            observerList.add(observer);
        }
    }

    /**
     * 移除一个下载监听器对象
     * @param observer
     */
    public void unregisterDownloadObserver(DownloadObserver observer){
        if(observerList.contains(observer)){
            observerList.remove(observer);
        }
    }

    /**
     * 下载监听器
     */
    public interface DownloadObserver{
        /**
         * 下载数据更新的方法
         */
        void onDownloadUpdate(DownloadInfo downloadInfo);
    }
}


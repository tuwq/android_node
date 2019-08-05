package com.tuwq.googleplay95.http.download;

import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.tuwq.googleplay95.bean.AppInfo;

import java.io.File;

/**
 * 用来封装下载任务相关数据
 */
public class DownloadInfo {

    //不让xutil内部对id进行自增长
    @NoAutoIncrement
    public int id;//下载任务的唯一标识，存取的时候用到
    public int state;//下载状态
    public String downloadUrl;//下载地址
    public long currentLength;//当前已经下载的长度
    public long size;//总的大小
    public String path;//下载文件保存的完整的绝对路径

    /**
     * 实例化DownloadInfo对象
     * @return
     */
    public static DownloadInfo create(AppInfo appInfo){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = appInfo.id;
        downloadInfo.downloadUrl = appInfo.downloadUrl;
        downloadInfo.size = appInfo.size;
        downloadInfo.state = DownloadManager.STATE_NONE;//默认是未下载
        downloadInfo.currentLength = 0;

        //下载文件的绝对路径: /mnt/sdcard/包名/download/有缘网.apk
        downloadInfo.path = DownloadManager.DOWNLOAD_DIR + File.separator
                + appInfo.name + ".apk";

        return downloadInfo;
    }
}

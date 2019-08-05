package com.tuwq.googleplay95.module;

import android.widget.Button;
import android.widget.ProgressBar;

import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.http.download.DownloadInfo;
import com.tuwq.googleplay95.http.download.DownloadManager;
import com.tuwq.googleplay95.util.ApkUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class DetailDownloadModule extends BaseModule<AppInfo> implements DownloadManager.DownloadObserver{
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.btn_download)
    Button btnDownload;

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_download;
    }

    private AppInfo appInfo;
    @Override
    public void bindData(AppInfo appInfo) {
        this.appInfo = appInfo;
        DownloadManager.create().registerDownloadObserver(this);

        //根据state来刷新UI
        DownloadInfo downloadInfo = DownloadManager.create().getDownloadInfo(appInfo);
        if(downloadInfo!=null){
            onDownloadUpdate(downloadInfo);
        }
    }

    @OnClick(R.id.btn_download)
    public void onClick() {
        //获取对应的downladInfo
        DownloadInfo downloadInfo = DownloadManager.create().getDownloadInfo(appInfo);
        if (downloadInfo==null){
            //说明这是第一次下载
            DownloadManager.create().download(appInfo);
        }else {
            //说明不是第一次下载,需要根据state来判断
            if(downloadInfo.state==DownloadManager.STATE_DOWNLOADING
                    || downloadInfo.state==DownloadManager.STATE_WAITING){
                //应该暂停
                DownloadManager.create().pause(appInfo);
            }else if(downloadInfo.state==DownloadManager.STATE_PAUSE
                    || downloadInfo.state==DownloadManager.STATE_ERROR){
                //应该继续下载
                DownloadManager.create().download(appInfo);
            }else if(downloadInfo.state==DownloadManager.STATE_FINISH){
                //需要安装
                ApkUtils.install(downloadInfo.path);
            }
        }
    }


    public void removeObserver(){
        DownloadManager.create().unregisterDownloadObserver(this);
    }


    @Override
    public void onDownloadUpdate(DownloadInfo downloadInfo) {
        if(appInfo==null || appInfo.id!=downloadInfo.id){
            //说明不是同一个，那么则不需要更新UI
            return;
        }

        switch (downloadInfo.state){
            case DownloadManager.STATE_NONE:
                btnDownload.setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                int progress = caculateProgress(downloadInfo);
                btnDownload.setText(progress+"%");
                break;
            case DownloadManager.STATE_PAUSE:
                btnDownload.setText("继续下载");
                caculateProgress(downloadInfo);
                break;
            case DownloadManager.STATE_FINISH:
                btnDownload.setText("安装");
                break;
            case DownloadManager.STATE_ERROR:
                btnDownload.setText("失败，重下");
                break;
            case DownloadManager.STATE_WAITING:
                btnDownload.setText("等待中...");
                break;
        }
    }

    private int caculateProgress(DownloadInfo downloadInfo) {
        int progress = (int) (downloadInfo.currentLength*100f/downloadInfo.size+0.5f);
        //1.下载按钮需要显示百分比
        btnDownload.setBackgroundResource(0);//移除掉btn的背景
        //2.进度条需要显示进度
        pbProgress.setProgress(progress);
        return progress;
    }

}

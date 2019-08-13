package com.tuwq.mobileplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tuwq.mobileplayer.bean.MusicBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AudioService extends Service {
    private static final String TAG = "AudioService";

    private AudioBinder mAudioBinder;
    private ArrayList<MusicBean> beanArrayList;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBinder = new AudioBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        beanArrayList = (ArrayList<MusicBean>) intent.getSerializableExtra("data");
        position = intent.getIntExtra("position", -1);
        // 数据可用性验证
        if (beanArrayList == null || beanArrayList.size() == 0 || position == -1) {
            return null;
        }
        // 播放选中的歌曲
        mAudioBinder.playItem();
        return mAudioBinder;
    }

    public class AudioBinder extends Binder{
        private class OnAudioPreparedListener implements MediaPlayer.OnPreparedListener {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 资源准备完成，开始播放
                mediaPlayer.start();
                // 获取当前正在播放的歌曲
                MusicBean musicBean = beanArrayList.get(position);
                // 通知 Activity 更新界面
                Intent intent = new Intent("com.tuwq.audio_prepared");
                intent.putExtra("musicBean",musicBean);
                sendBroadcast(intent);
            }
        }
        private MediaPlayer mediaPlayer;
        // 播放当前 position 指定的歌曲
        private void playItem(){
            // 获取当前要播放的歌曲
            MusicBean musicBean = beanArrayList.get(position);
            LogUtils.e(TAG, "AudioService.onBind,musicBean=" + musicBean);
            try {
                // 播放音乐
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(musicBean.path);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new OnAudioPreparedListener());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 如果当前正在播放歌曲，则暂停；否则就开启播放
        public void switchPauseStatus(){
            if (mediaPlayer.isPlaying()){
                // 正在播放，需要暂停
                mediaPlayer.pause();
            }else{
                // 暂停状态，恢复播放
                mediaPlayer.start();
            }
        }
        // 返回 true 说明当前是播放状态
        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }
        // 停止播放，释放歌曲资源
        public void stop(){
            mediaPlayer.stop();
        }
    }
}

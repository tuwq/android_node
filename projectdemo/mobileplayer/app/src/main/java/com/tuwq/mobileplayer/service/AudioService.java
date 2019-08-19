package com.tuwq.mobileplayer.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tuwq.mobileplayer.bean.MusicBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AudioService extends Service {
    private static final String TAG = "AudioService";
    public static final int PLAYMODE_ALL = 0;
    public static final int PLAYMODE_SINGLE = 1;
    public static final int PLAYMODE_RANDOM = 2;
    private int mPlayMode = PLAYMODE_ALL;

    private AudioBinder mAudioBinder;
    private ArrayList<MusicBean> beanArrayList;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBinder = new AudioBinder();
        // 获取播放模式的缓存
        mPlayMode = getSharedPreferences("config",MODE_PRIVATE).getInt("playmode",PLAYMODE_ALL);
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
        private class OnAudioCompletionListener implements MediaPlayer.OnCompletionListener {
            @Override
            // 歌曲播放结束
            public void onCompletion(MediaPlayer mp) {
                autoPlayNext();
            }
        }
        private MediaPlayer mediaPlayer;
        // 播放当前 position 指定的歌曲
        private void playItem(){
            // 获取当前要播放的歌曲
            MusicBean musicBean = beanArrayList.get(position);
            LogUtils.e(TAG, "AudioService.onBind,musicBean=" + musicBean);
            try {
                if (mediaPlayer != null) {
                    // 换歌置空
                    mediaPlayer.reset();
                } else {
                    // 播放音乐
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.setDataSource(musicBean.path);
                mediaPlayer.prepareAsync();
                // 播放器启动监听
                mediaPlayer.setOnPreparedListener(new OnAudioPreparedListener());
                // 播放器结束监听
                mediaPlayer.setOnCompletionListener(new OnAudioCompletionListener());
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

        // 返回音乐的总时长,用于进度条
        public int getDuration(){
            return mediaPlayer.getDuration();
        }
        // 返回音乐的当前时长,用于进度条
        public int getPosition(){
            return mediaPlayer.getCurrentPosition();
        }
        // 跳转到指定毫秒处播放
        public void seekTo(int msec){
            mediaPlayer.seekTo(msec);
        }
        // 播放上一首歌
        public void playPre(){
            if (position!=0){
                position--;
                playItem();
            }else {
                Toast.makeText(AudioService.this, "已经是第一首歌了！", Toast.LENGTH_SHORT).show();
            }
        }
        // 播放下一首歌
        public void playNext(){
            if (position!=beanArrayList.size()-1){
                position++;
                playItem();
            }else {
                Toast.makeText(AudioService.this, "已经是最后一首歌了！", Toast.LENGTH_SHORT).show();
            }
        }

        // 按照 顺序播放->单曲循环->随机播放 依次切换
        public void switchPlayMode(){
            switch (mPlayMode){
                case PLAYMODE_ALL:
                    mPlayMode = PLAYMODE_SINGLE;
                    break;
                case PLAYMODE_SINGLE:
                    mPlayMode = PLAYMODE_RANDOM;
                    break;
                case PLAYMODE_RANDOM:
                    mPlayMode = PLAYMODE_ALL;
                    break;
            }
            // 保存播放顺序到配置文件
            SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor edit = config.edit();
            edit.putInt("playmode",mPlayMode);
            edit.commit();
        }

        /**
         * 返回当前正在使用的播放模式
         * @return
         */
        public int getPlayMode(){
            return mPlayMode;
        }

        // 根据播放模式自动播放下一首歌
        private void autoPlayNext() {
            switch (mPlayMode){
                case PLAYMODE_ALL:
                    if(position!=beanArrayList.size()-1){
                        position++;
                    }else{
                        position = 0;
                    }
                    break;
                case PLAYMODE_SINGLE:
                    break;
                case PLAYMODE_RANDOM:
                    position = new Random().nextInt(beanArrayList.size());
                    break;
            }
            // 播放当前选中的歌曲
            playItem();
        }
    }
}

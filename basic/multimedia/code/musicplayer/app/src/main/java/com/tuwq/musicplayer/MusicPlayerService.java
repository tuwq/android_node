package com.tuwq.musicplayer;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MusicPlayerService extends Service {

    private String path = "mnt/sdcard/xpg.mp3";
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyBinder extends Binder {
        /**
         * 播放暂停切换
         */
        public void playPause(){
            if(player.isPlaying()){
                player.pause();
            }else{
                player.start();
            }
        }

        /**
         * 播放状态
         * @return
         */
        public boolean isPlaying(){
            return player.isPlaying();
        }

        /**
         * 总时间
         */
        public int getDuration(){
            return player.getDuration();
        }

        /**
         * 当前时间
         * @return µ±Ç°²¥·Å½ø¶ÈµÄºÁÃëÖµ
         */
        public int getCurrentPosition(){
            return player.getCurrentPosition();
        }

        /**
         * 跳转到
         * @param msec
         */
        public void seekTo(int msec){
            player.seekTo(msec);
        }
    }

}

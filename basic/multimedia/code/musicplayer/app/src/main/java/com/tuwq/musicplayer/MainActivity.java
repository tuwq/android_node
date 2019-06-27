package com.tuwq.musicplayer;

import com.tuwq.musicplayer.MusicPlayerService.MyBinder;

import android.media.MediaPlayer;
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

public class MainActivity extends Activity {

    private static final int UPDATE_PROGRESS = 0;
    public String path = "mnt/sdcard/xpg.mp3";
    private MyConnection conn;
    private MyBinder musicController;
    private ImageButton ib_play;
    private SeekBar sb_progress;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_play = (ImageButton) findViewById(R.id.ib_play);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);
        sb_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if(fromUser){
                    musicController.seekTo(progress);
                }
            }
        });

        Intent service = new Intent(this, MusicPlayerService.class);
        startService(service);
        conn = new MyConnection();
        bindService(service, conn, BIND_AUTO_CREATE);
        System.out.println("onCreate");
    }

    public void play(View v) {
        musicController.playPause();
        updatePlayIcon();
    }

    /**
     * 修改进度条
     * 不断延迟调用自身
     */
    private void updateProgress(){
        System.out.println("updateProgress");
        int currentPosition = musicController.getCurrentPosition();
        sb_progress.setProgress(currentPosition);
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }

    private void updatePlayIcon() {
        if(musicController.isPlaying()){
            ib_play.setImageResource(R.drawable.btn_audio_pause);
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }else{
            ib_play.setImageResource(R.drawable.btn_audio_play);
            handler.removeMessages(UPDATE_PROGRESS);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(musicController!=null){
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
        System.out.println("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    class MyConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("onServiceConnected");
            musicController = (MyBinder) service;
            updatePlayIcon();
            sb_progress.setMax(musicController.getDuration());
            sb_progress.setProgress(musicController.getCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}

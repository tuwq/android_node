package com.tuwq.videoplayerbymediaplay;


import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class MainActivity extends Activity {

    private SurfaceView surface;
    private String path = "http://192.168.147.3:9001/img/oppo.mp4";
    private SurfaceHolder holder;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surface = (SurfaceView) findViewById(R.id.surface);
        holder = surface.getHolder();
    }


    public void prepare(View v){
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepareAsync();
            player.setDisplay(holder);
            player.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(View v){
        if(player !=null){
            if(player.isPlaying()){
                player.pause();
            }else{
                player.start();
            }
        }
    }
}

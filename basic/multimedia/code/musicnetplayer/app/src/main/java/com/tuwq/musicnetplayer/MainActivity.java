package com.tuwq.musicnetplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private MediaPlayer player;
    private String path = "http://192.168.147.3:9001/img/xpg.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void play(View v){
        if(player == null){
            player = new MediaPlayer();
            try {
                player.setDataSource(path);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            if(player.isPlaying()){
                player.pause();
            }else{
                player.start();
            }
        }
    }

}

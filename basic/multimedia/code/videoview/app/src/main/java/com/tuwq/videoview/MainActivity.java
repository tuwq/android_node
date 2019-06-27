package com.tuwq.videoview;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    private VideoView vv_video;
    private String path = "http://192.168.147.3:9001/img/rmvb.rmvb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_main);
        vv_video = (VideoView) findViewById(R.id.vv_video);
    }

    public void prepare(View v){
        //ÉèÖÃÊÓÆµµÄÂ·¾¶ setVideoPath·½·¨ÖÐ ´´½¨ÁËÒ»¸ömediaPlayer¶ÔÏó  µ÷ÓÃÁËsetDataSource
        //µ÷ÓÃÁËÒì²½×¼±¸µÄ·½·¨
        vv_video.setVideoPath(path);
        //ÉèÖÃ¼àÌý
        vv_video.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv_video.start();
            }
        });
        //ÉèÖÃÄ¬ÈÏµÄ½ø¶ÈÌõ
        vv_video.setMediaController(new MediaController(this));
    }

    public void start(View v){

    }
}

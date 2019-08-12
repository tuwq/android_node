package com.tuwq.mobileplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tuwq.mobileplayer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {
    @Bind(R.id.jiecaoplayer)
    JCVideoPlayerStandard jiecaoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        jiecaoplayer.setUp(url,title);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}

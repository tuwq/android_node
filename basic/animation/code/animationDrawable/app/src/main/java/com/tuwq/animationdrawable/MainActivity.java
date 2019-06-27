package com.tuwq.animationdrawable;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);

        // 创建动画
        AnimationDrawable animation = (AnimationDrawable) iv_image.getBackground();
        animation.start();
    }

}

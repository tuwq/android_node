package com.tuwq.mobileplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tuwq.mobileplayer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预加载界面
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_iv_bg)
    ImageView splashIvBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // 播放缩放动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            /**
             * 动画开始
             * @param animation
             */
            @Override
            public void onAnimationStart(Animation animation) {

            }
            /**
             * 动画结束
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                startMainActivity();
            }
            /**
             * 动画重复播放
             * @param animation
             */
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashIvBg.startAnimation(animation);
    }

    /**
     * 前往主界面
     */
    private void startMainActivity() {
        // 打开主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // 关闭当前界面
        finish();

        // 动态设置界面转场效果
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}

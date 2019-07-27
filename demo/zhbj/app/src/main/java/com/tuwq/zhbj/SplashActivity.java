package com.tuwq.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.tuwq.zhbj.tool.Constants;
import com.tuwq.zhbj.tool.SharedPreferencesTool;

public class SplashActivity extends Activity {

    RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        mRoot = this.findViewById(R.id.splash_rel_root);
        // 执行动画操作
        setAnimation();
    }

    /**
     * 执行界面动画
     */
    private void setAnimation() {
        // 旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        // 缩放 fromX:起始控件尺寸 toX: 结束控件尺寸
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        // 渐变
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);// 透明到不透明效果
        alphaAnimation.setDuration(2000);
        // 组合
        // 是否使用同一动画插补器,true使用,false不使用
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        // 执行动画
        mRoot.startAnimation(animationSet);

        // 动画执行结束,跳转到引导界面/首页
        // 监听动画操作
        animationSet.setAnimationListener(animationListener);
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        // 动画开始时调用
        @Override
        public void onAnimationStart(Animation animation) {

        }
        // 动画结束时调用
        @Override
        public void onAnimationEnd(Animation animation) {
            // 跳转到引导界面或者是首页
            enter();
        }
        // 动画重复执行时调用
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 跳转到引导界面或者是首页
     */
    private void enter() {
        // 问题: 如何知道跳转到首页还是引导界面
        // 获取是否第一次进入的操作,如果第一次进入,跳转到引导界面,如果不是第一次,跳转到首页
        boolean b = SharedPreferencesTool.getBoolean(SplashActivity.this, Constants.ISFIRSTENTER, true);
        if (b) {
            // 第一次进入,跳转到引导界面
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
        } else {
            // 不是第一次进入,跳转到首页
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        // 跳转界面完成,移除Splash界面
        finish();
    }
}

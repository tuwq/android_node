package com.tuwq.mobilesafe;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends Activity {

    View mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mLogo = this.findViewById(R.id.home_iv_logo);
        // 实现logo旋转动画
        setAnimation();
    }

    /**
     * logo旋转动画
     */
    private void setAnimation() {
        /*mLogo.setRotationX(); // 根据x轴进行旋转
        mLogo.setRotationY(); // 根据y轴进行旋转
        mLogo.setRotation(); // 根据z轴进行旋转*/
        /**
         * arg1: 执行动画的控件
         * arg2: 执行动画的方法的名称
         * arg3: 执行动画所需的参数
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLogo, "rotationY", 0f, 90f, 270f, 360f);
        objectAnimator.setDuration(2000); // 设置持续时间
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE); // 设置动画执行次数 INFINITE一直执行
        // RESTART 每次旋转从开始的位置旋转
        // REVERSE 每次旋转从结束的位置旋转
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);// 设置动画执行类型
        objectAnimator.start(); // 执行动画
    }
}

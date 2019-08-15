package com.tuwq.imclient.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.tuwq.imclient.MainActivity;
import com.tuwq.imclient.R;
import com.tuwq.imclient.presenter.SplashPresenter;
import com.tuwq.imclient.presenter.SplashPresenterImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActvity extends BaseActivity implements SplashView {
    @InjectView(R.id.iv_splash)
    ImageView ivSplash;
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        splashPresenter = new SplashPresenterImpl(this);
        //判断是否登录过
        splashPresenter.checkLogin();
    }

    @Override
    public void onGetLoginState(boolean isLogin) {
        if (isLogin) {
            //如果登录过跳到主界面
            startActivity(MainActivity.class, true);
        } else {
            //如果没有登录跳到登录的界面
            ObjectAnimator alpha = ObjectAnimator.ofFloat(ivSplash, "alpha", 0, 1).setDuration(2000);
            alpha.start();
            alpha.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(LoginActivity.class, true);
                }
            });
        }
    }
}

package com.tuwq.imclient.splash.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.tuwq.imclient.splash.presenter.SplashPresenter;
import com.tuwq.imclient.splash.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView splashView;

    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
    }

    /**
     * 是否登录
     */
    @Override
    public void checkLogin() {
        //检测是登录过
        if(EMClient.getInstance().isLoggedInBefore()){
            if(EMClient.getInstance().isConnected()){
                //isLoggedInBefore 之前登陆过    isConnected 已经跟环信的服务器建立了连接
                splashView.onGetLoginState(true);
            }
        }else{
            splashView.onGetLoginState(false);
        }
        //splashView.onGetLoginState();
    }
}

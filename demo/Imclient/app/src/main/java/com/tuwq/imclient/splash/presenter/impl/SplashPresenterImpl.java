package com.tuwq.imclient.splash.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.tuwq.imclient.splash.presenter.SplashPresenter;
import com.tuwq.imclient.splash.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    /**
     * view的接口
     */
    private SplashView splashView;

    /**
     * 构造   构造的时候传入view接口的具体实现 通过这个实现 调用View层的业务逻辑
     * @param splashView
     */
    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void checkLogin() {
        //检测是登录过
        if(EMClient.getInstance().isLoggedInBefore()&&EMClient.getInstance().isConnected()){
            // if(EMClient.getInstance().isConnected()){
            //isLoggedInBefore 之前登陆过    isConnected 已经跟环信的服务器建立了连接
            splashView.onGetLoginState(true);
            // }

        }else{
            splashView.onGetLoginState(false);
        }
        //splashView.onGetLoginState();
    }
}

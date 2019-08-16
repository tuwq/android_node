package com.tuwq.imclient.splash.view;

public interface SplashView {
    /**
     * 获取当前设备的登录状态之后 要处理的界面跳转逻辑
     * @param isLogin 是否已经登录了
     */
    void onGetLoginState(boolean isLogin);
}

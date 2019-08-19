package com.tuwq.imclient.splash.view;

public interface LoginView {
    /**
     *
     * @param username
     * @param isLogin
     * @param errorMsg
     */
    void onGetLoginState(String username,boolean isLogin,String errorMsg);
}

package com.tuwq.imclient.main.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.tuwq.imclient.callback.MyEmCalBack;
import com.tuwq.imclient.splash.presenter.LoginPresenter;
import com.tuwq.imclient.splash.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(final String username, final String pwd) {
        EMClient.getInstance().login(username, pwd, new MyEmCalBack() {
            @Override
            public void success() {
                loginView.onGetLoginState(username,true,null);
            }

            @Override
            public void error(int i, String s) {
                loginView.onGetLoginState(username,false,s);
            }
        });

    }
}

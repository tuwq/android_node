package com.tuwq.imclient.main.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.tuwq.imclient.callback.MyEmCalBack;
import com.tuwq.imclient.main.presenter.LogoutPresenter;
import com.tuwq.imclient.main.view.PluginView;

public class LogoutPresenterImpl implements LogoutPresenter {
    private PluginView pluginView;

    public LogoutPresenterImpl(PluginView pluginView) {
        this.pluginView = pluginView;
    }

    @Override
    public void logout() {
        EMClient.getInstance().logout(true, new MyEmCalBack() {
            @Override
            public void success() {
                pluginView.onLogout(true,null);
            }

            @Override
            public void error(int i, String s) {
                pluginView.onLogout(false,s);
            }
        });
    }
}

package com.tuwq.imclient.callback;

import com.hyphenate.EMCallBack;
import com.tuwq.imclient.utils.ThreadUtils;

public abstract class MyEmCalBack implements EMCallBack {
    /**
     * 在主线程中处理环信操作成功的回调方法
     */
    public abstract void success();
    /**
     * 在主线程中处理环信操作失败的回调方法
     */
    public abstract void error(int i ,String s);
    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                success();
            }
        });
    }

    @Override
    public void onError(final int i, final String s) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                error(i,s);
            }
        });

    }

    @Override
    public void onProgress(int i, String s) {

    }
}

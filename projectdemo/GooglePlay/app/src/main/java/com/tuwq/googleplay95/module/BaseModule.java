package com.tuwq.googleplay95.module;

import android.view.View;

import com.tuwq.googleplay95.global.MyApp;

import butterknife.ButterKnife;

/**
 * 负责完成模块类的公共方法定义
 */

public abstract class BaseModule<T> {
    View moduleView;

    public BaseModule(){
        moduleView = View.inflate(MyApp.context, getLayoutId(),null);
        ButterKnife.bind(this,moduleView);
    }

    public View getModuleView(){
        return moduleView;
    }

    /**
     * 初始化模块的View
     */
    public abstract int getLayoutId();

    /**
     * 绑定数据
     */
    public abstract void bindData(T data);
}

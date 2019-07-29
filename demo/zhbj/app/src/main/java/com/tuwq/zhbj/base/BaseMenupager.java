package com.tuwq.zhbj.base;

import android.app.Activity;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tuwq.zhbj.HomeActivity;

/**
 * 菜单页页面的父类
 * 因为父类不知道子类加载什么样的界面，显示什么样的数据，所以创建抽象方法，子类根据自己的特性进行实现
 */
public abstract class BaseMenupager {
    public Activity activity;
    public View rootView;
    public SlidingMenu slidingMenu;
    public BaseMenupager(Activity activity){
        this.activity = activity;
        slidingMenu = ((HomeActivity)activity).getSlidingMenu();
        rootView = initView();
    }

    /**
     * 加载布局
     *@return
     */
    public abstract View initView();

    /**
     * 显示数据
     */
    public abstract void initData();
}

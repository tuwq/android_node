package com.tuwq.zhbj.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 首页和菜单页的fragment的父类
 */
public abstract class BaseFragment extends Fragment {
    public Activity activity;

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //getActivity : 获取管理fragment的activity
        activity = this.getActivity();
        super.onCreate(savedInstanceState);
    }

    /**
     * 加载fragment布局的方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //因为fragment加载布局的操作是否调用onCreateView方法实现的，不是通过initView方法实现的
        View view = initView();
        return view;
    }

    /**
     * 加载显示数据的方法，类似activity的oncrate方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        super.onActivityCreated(savedInstanceState);
    }


    //因为父类不知道子类要加载什么布局，显示什么数据，所以，创建抽象方法，子类实现抽象方法，根据自己的特性进行相应的实现
    /**
     * 加载布局的方法，由子类进行实现
     *@return
     */
    public abstract View initView();
    /**
     * 显示数据的方法，由子类进行实现
     */
    public abstract void initData();
}

package com.tuwq.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tuwq.zhbj.R;

/**
 * 首页，新闻中心，智慧服务等界面的父类
 */
public class BasePager {

    public Activity activity;
    public View view;
    public TextView mTitle;
    public FrameLayout mContent;
    public ImageButton mMenu;

    public BasePager(Activity activity){
        this.activity = activity;
        // 在new出来的界面的时候，就加载界面
        view = initView();
    }

    /**
     * 加载布局
     *@return
     */
    public View initView(){
        //因为每个子类都是标题+显示内容的操作，相同操作收取到父类
        view = View.inflate(activity, R.layout.basepager, null);

        //初始化控件，方便填充显示子类的数据
        mTitle = (TextView) view.findViewById(R.id.titlebar_tv_title);
        mContent = (FrameLayout) view.findViewById(R.id.basepager_fl_content);
        mMenu = (ImageButton) view.findViewById(R.id.titlebar_btn_menu);

        return view;
    }

    /**
     * 显示数据
     */
    public void initData(){

    }
}

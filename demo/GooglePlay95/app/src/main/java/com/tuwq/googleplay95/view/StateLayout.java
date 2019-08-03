package com.tuwq.googleplay95.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.tuwq.googleplay95.R;

/**
 * 选择FrameLayout的原因：FrameLayout代码量最少，最轻量级！
 */
public class StateLayout extends FrameLayout{

    private View loadingView;
    private View errorView;
    private View successView;

    public StateLayout(Context context) {
        this(context,null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化View
        initView();
    }

    /**
     * 添加那3个子View：加载中的，加载成功的，加载失败的
     */
    private void initView() {
        //1.加载loadingView
        loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        addView(loadingView);

        //2.添加失败的View
        errorView = View.inflate(getContext(), R.layout.page_error, null);
        addView(errorView);

        //3.由于成功的View是动态的，所以提供一个方法，让外界传入

        //一开始隐藏所有的View
        hideAll();
    }

    /**
     * 设置一个成功的View进来
     */
    public void bindSuccessView(View view){
        successView = view;

        if(successView!=null){
            successView.setVisibility(View.INVISIBLE);//隐藏successView
            //将它添加进来
            addView(successView);
        }
    }

    public void showSuccessView(){
        //先隐藏其他的
        hideAll();

        if(successView!=null){
            successView.setVisibility(View.VISIBLE);
        }
    }

    public void showErrorView(){
        //先隐藏其他的
        hideAll();

        errorView.setVisibility(View.VISIBLE);
    }

    public void showLoadingView(){
        //先隐藏其他的
        hideAll();

        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏所有的View
     */
    public void hideAll(){
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        if(successView!=null){
            successView.setVisibility(View.INVISIBLE);
        }
    }
}

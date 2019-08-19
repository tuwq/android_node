package com.tuwq.zhbj.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RollViewPager extends ViewPager {

    private int downY;
    private int downX;

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://默认永远都是不会拦截的
                //getParent : 获取viewpager的父控件
                getParent().requestDisallowInterceptTouchEvent(true);//请求父控件不要拦截下一个事件,true：不拦截   false:拦截，控制的下一个事件

                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                //判断是上下还是左右的操作
                if (Math.abs(downX-moveX) > Math.abs(downY - moveY)) {
                    //左右滑动
                    //从右往左
                    if (downX-moveX > 0 && getCurrentItem() == getAdapter().getCount()-1) {
                        //父控件拦截事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(downX-moveX > 0 && getCurrentItem() < getAdapter().getCount()-1){
                        //父控件不拦截事件，切换下一个图片
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    //从左往右
                    else if(downX-moveX < 0 && getCurrentItem() == 0){
                        //父控件拦截事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(downX - moveX < 0 && getCurrentItem() > 0){
                        //父控件不拦截事件，切换到上一个图片
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //上下
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

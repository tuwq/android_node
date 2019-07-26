package com.tuwq.slidingmenu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

public class MySlidingMenu extends ViewGroup {

    View menuView;
    View mainView;
    int menuWidth;
    Scroller scroller;

    private int downX;
    /* 保存上一次移动位置 */
    private int nextStartIndx = 0;
    private int distance;
    private int downY;

    public MySlidingMenu(Context context) {
        this(context, null);
    }

    public MySlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MySlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
    }

    /**
     * 测量宽高
     * 自定义控件
     * 首页控件
     * 菜单页控件
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 自定义控件,match_parent的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取子控件,根据子控件索引获取子控件,索引: 布局从上往下,依次从0开始
        menuView = this.getChildAt(0);
        mainView = this.getChildAt(1);
        // 首页控件,除了设置规则之外也是可以设置宽高,如果子控件有宽高,可以直接作为参数设置
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        // 菜单页控件,固定的240dp
        menuWidth = menuView.getLayoutParams().width;
        menuView.measure(menuWidth, heightMeasureSpec);
    }

    /**
     * 排版
     * @param changed
     * @param l 相当于距离父控件的填充距离 相对于父控件的位置
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 首页
        mainView.layout(l, t, r, b);
        // 菜单页
        menuView.layout(l-240, t, l,b);
    }

    /**
     * 绘制界面,布局文件中已绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 实现触摸事件的,移动自定义侧拉菜单
     * 获取按下的x坐标,再获取移动的x坐标,让自定义的侧拉菜单移动两个坐标之间的距离
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int)event.getX();
                distance = nextStartIndx + moveX - downX;
                if (distance < 0) {
                    distance = 0;
                } else if (distance > menuWidth) {
                    distance = menuWidth;
                }
                // 自定义侧拉菜单移动相应的距离
                myScrollTo(distance); // 移动相应的距离,x和y: x轴h和y轴的距离
                break;
            case MotionEvent.ACTION_UP:
                // 抬起鼠标的时候,保存上一次移动的距离,方便下一次移动的设置移动的起始位置
                // 自动滑动效果
                if (distance < menuWidth/2) {
                    nextStartIndx = 0;
                } else {
                    nextStartIndx = menuWidth;
                }
                scrollToTime(distance, nextStartIndx);
                break;
        }
        return true;
    }

    /**
     * 打开关闭侧拉菜单操作
     */
    public void toggle() {
        // 判断移动距离，如果是0，是关闭状态，执行打开，如果是菜单页的宽度，是打开的状态，执行关闭
        // 获取scrollTo移动距离，也是正负相反
        if (myGetScroll() == 0) {
            // 是关闭状态，执行打开
            distance = 0;
            nextStartIndx = menuWidth;
        } else {
            // 是打开的状态，执行关闭
            distance = menuWidth;
            nextStartIndx = 0;
        }
        scrollToTime(distance, nextStartIndx);
    }

    /**
     * 事件分发，用来判定是否将触摸事件分发给子控件
     * 主要是判定是否分发事件，具体的判断操作由onInterceptTouchEvent实现
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是dispatchTouchEvent调用，具体判断操作由它来做
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 判断，如果是上下滑动，return false：不拦截传递给scrollview，如果左右滑动，return true：拦截事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int distanceX = moveX - downX;
                int distanceY = moveY - downY;
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    // 拦截,执行自己的onTouchEvent
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 子控件draw的固定写法
     */
    @Override
    public void computeScroll() {
        // 判断是否还有移动的小段
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX(); // 获取移动的小段
            this.myScrollTo(currX);
            invalidate();
        }
        super.computeScroll();
    }

    /**
     * 纠正scrollTo正负问题
     * @param x
     */
    private void myScrollTo(int x) {
        this.scrollTo(-x, 0);
        // this.scrollBy();// 每次再上一次基础上累加 10->10 20->30 30->60
    }

    /**
     * 纠正getScroll方法正负的问题
     * @return
     */
    private float myGetScroll() {
        return -getScrollX();
    }

    /**
     * 缓慢动画
     */
    private void scrollToTime(int startX, int toX) {
        int dx = toX -startX;

        // 根据移动距离设置相应的事件
        // 获取每段的时间
        int scaleTime = 1000 / menuWidth;
        // 获取移动距离所花的时间
        int time = scaleTime * Math.abs(dx);
        /**
         * 将移动的距离拆分成一段一段的
         * @param startX,Y 开始位置
         * @param dx,y 移动的距离
         * @param duration 持续的时间
         */
        scroller.startScroll(startX, 0, dx, 0, time);
        invalidate();
    }
}

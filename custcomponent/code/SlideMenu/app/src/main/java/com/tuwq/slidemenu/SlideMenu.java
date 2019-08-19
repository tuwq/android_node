package com.tuwq.slidemenu;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * 当我的viewGroup对于view的测量没有特殊需求时,可以不用自己实现,而应该通过
 * 继承系统已有的布局,让它帮我实现
 */
public class SlideMenu extends FrameLayout {

    private ViewDragHelper dragHelper;
    private View menu;
    private View main;
    int maxLeft;//最大能移动到的left

    FloatEvaluator floatEvaluator = new FloatEvaluator();
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    /**
     * 定义状态常量
     */
    public enum SlideState{
        Open,Close
    }
    private SlideState mState = SlideState.Close;//当前的状态，


    public SlideState getState(){
        return mState;
    }

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //    Scroller scroller;
    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        scroller = new Scroller(getContext());

        dragHelper = ViewDragHelper.create(this, callback);
    }

    /**
     * 就是布局文件的结构解析完毕之后执行，此时就知道了自己有几个子View，而且该方法
     * 在onMeasure执行之前，所以在该方法中还获取不到VIew的宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //判断子View的数量
        if(getChildCount()!=2){
            throw new IllegalArgumentException("SlideMenu only can have 2 children!");
        }

        menu = getChildAt(0);
        main = getChildAt(1);
    }

    /**
     * 当onMeasure执行完毕之后调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxLeft = (int) (getMeasuredWidth()*0.6f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //让ViewDragHelper来帮我们判断是否应该拦截
        boolean result = dragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        //将触摸事件交给DragHelper来处理
        dragHelper.processTouchEvent(event);

        return true;
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 用来判断是否要捕获监视View的触摸事件
         * @param child     当前触摸的子View
         * @param pointerId     触摸点的索引
         * @return true:表示需要捕获    false：忽略不处理
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 两个child都可以触摸
//            return child == main || child==menu;
            return true;
        }

        /**
         * 很鸡肋的一个方法，表面上看来是用来获取水平拖拽范围的，而事实上它的返回值
         * 是用来判断是否想横向滑动的条件之一,通过返回一个大于0的值，可以强制水平滑动
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        /**
         * 修正View水平方向的位置
         * @param child     当前触摸的子View
         * @param left  表示ViewDragHelper帮你算好的child的left变成的值，left=child.getLeft()+dx
         * @param dx    表示本次移动的距离
         * @return      返回的值表示我们真正想让child的left变成的值
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //只对main进行限制
            if(child==main){
                left = fixLeft(left);
            }
            return left;
        }
        /**
         * 修正View垂直方向的位置
         * @param child     当前触摸的子View
         * @param top  表示ViewDragHelper帮你算好的child的top变成的值，top=child.getTop()+dy
         * @param dy    表示本次移动的距离
         * @return      返回的值表示我们真正想让child的top变成的值
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        /**
         * 当View的位置改变了的回调
         * @param changedView   当前位置改变的View
         * @param left     改变后最新的left
         * @param top      改变后最新的top
         * @param dx       本次水平移动的距离
         * @param dy       本次垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
//            Log.e("tag","left: "+left   +   "  dx:"+dx);
            //判断如果changedView是menu，让main跟随移动
            if(changedView==menu){
                //让menu始终固定在原点位置
                menu.layout(0,menu.getTop(),menu.getMeasuredWidth(),menu.getBottom());

                //让main同时移动dx的值
                int newLeft = main.getLeft()+dx;
                newLeft = fixLeft(newLeft);//对left的值进行范围限制
                main.layout(newLeft,main.getTop(),newLeft+main.getMeasuredWidth(),main.getBottom());
            }

            //1.计算left滑动的百分比
            float fraction = main.getLeft()*1f/maxLeft;
//            Log.e("tag","fraction: "+fraction);
            //2.根据百分比执行伴随动画
            executeAnim(fraction);

            //3.回调接口的方法
            if(listener!=null){listener.onDraging(fraction);}
            if(fraction==0f && mState!=SlideState.Close){
                mState = SlideState.Close;

                if(listener!=null){
                    listener.onClose();
                }
            }else if(fraction==1f && mState!=SlideState.Open){
                mState = SlideState.Open;

                if(listener!=null){
                    listener.onOpen();
                }
            }
        }

        /**
         * 当View被抬起的时候执行
         * @param releasedChild  抬起的子View
         * @param xvel      x方向的滑动速度
         * @param yvel      y方向的滑动速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if(main.getLeft()>maxLeft/2){
                //应该打开
//                scroller.startScroll();
//                invalidate();//是为了调用computeScroll方法

                dragHelper.smoothSlideViewTo(main,maxLeft,main.getTop());
                ViewCompat.postInvalidateOnAnimation(SlideMenu.this);//是为了调用computeScroll方法
            }else {
                //应该关闭
                dragHelper.smoothSlideViewTo(main,0,main.getTop());
                ViewCompat.postInvalidateOnAnimation(SlideMenu.this);//是为了调用computeScroll方法
            }
        }
    };

    /**
     * 执行伴随动画
     * @param fraction
     */
    private void executeAnim(float fraction) {
        //fraction:0->1
        //scale: 1->0.8
        //value:10 -> 110
        //算法：start + (end-start)*fraction
//        float scale = 1f + (0.8f-1f)*fraction;
        //让main进行缩放
        main.setScaleX(floatEvaluator.evaluate(fraction,1f,0.8f));
        main.setScaleY(floatEvaluator.evaluate(fraction,1f,0.8f));

        //让menu执行缩放和平移
        menu.setScaleX(floatEvaluator.evaluate(fraction,0.5f,1f));
        menu.setScaleY(floatEvaluator.evaluate(fraction,0.5f,1f));

        //3D效果
        // menu.setRotationY(floatEvaluator.evaluate(fraction,-90,0));
        // main.setRotationY(floatEvaluator.evaluate(fraction,0,90));

        menu.setTranslationX(floatEvaluator.evaluate(fraction,-menu.getMeasuredWidth()/2,0));

        //给SlideMenu的背景图片添加阴影遮罩效果
        if(getBackground()!=null){
//            getBackground().setColorFilter((Integer) argbEvaluator.evaluate(fraction,Color.RED,Color.GREEN),
//                    PorterDuff.Mode.SRC_OVER);
            getBackground().setColorFilter((Integer) argbEvaluator.evaluate(fraction, Color.BLACK,Color.TRANSPARENT),
                    PorterDuff.Mode.SRC_OVER);
        }

    }

    /**
     * 在该方法中才能获取到模拟滚动的值
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //scroller的写法
//        if(scroller.computeScrollOffset()){
//            scrollTo(scroller.getCurrX(),scroller.getCurrY());
//            invalidate();
//        }

        //ViewDragHelper的写法
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(SlideMenu.this);
        }
    }

    private int fixLeft(int newLeft) {
        if(newLeft>maxLeft){
            newLeft = maxLeft;
        }else if(newLeft<0){
            newLeft = 0;
        }
        return newLeft;
    }

    private OnSlideChangeListener listener;
    public void setOnSlideChangeListener( OnSlideChangeListener listener){
        this.listener = listener;
    }

    public interface OnSlideChangeListener{
        void onOpen();
        void onClose();
        void onDraging(float fraction);
    }

}

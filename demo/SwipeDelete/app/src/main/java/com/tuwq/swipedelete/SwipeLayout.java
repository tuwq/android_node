package com.tuwq.swipedelete;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class SwipeLayout extends FrameLayout {

    private View content;
    private View delete;
    ViewDragHelper dragHelper;
    int touchSlop;//该变量是系统维护的，表示系统认为的介于点击和滑动时间的分界值

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, callback);

        //获取touchSlop的值,表示系统认为的介于点击和滑动时间的分界值
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /**
     * 就是布局文件的结构解析完毕之后执行，此时就知道了自己有几个子View，而且该方法
     * 在onMeasure执行之前，所以在该方法中还获取不到VIew的宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        content = getChildAt(0);
        delete = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        content.layout(0, 0, content.getMeasuredWidth(), content.getMeasuredHeight());
        delete.layout(content.getMeasuredWidth(), 0, content.getRight() + delete.getMeasuredWidth()
                , delete.getMeasuredHeight());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean result = dragHelper.shouldInterceptTouchEvent(ev);

        return result;
    }

    float downX,downY;
    long downTime;//按下的时间
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                //1.获取手指移动的距离
                float moveX = event.getX();
                float moveY = event.getY();
                float dx = moveX - downX;
                float dy = moveY - downY;
                //2.判断手指移动的距离到底是偏向于垂直还是水平
                if(Math.abs(dx)>Math.abs(dy)){
                    //我们认为用户想水平滑动条目，那么则请求父View不要拦截
                    requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                //在抬起的时候，通过判断时间点和距离来实现点击事件
                long duration = System.currentTimeMillis() - downTime;//按下抬起的时间

                float deltaX = event.getX()-downX;//按下到抬起的x距离
                float deltaY = event.getY()-downY;//按下到抬起的y距离
                float distance = (float) Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
                //如果duration小于400，并且distance小于8像素就认为触发了点击事件
                if(duration<400 && distance<touchSlop){
                    //能进来，则认为是点击了SwipeLayout
                    if(clickListener!=null){
                        clickListener.onClick();
                    }
                }

                break;
        }
        dragHelper.processTouchEvent(event);

        return true;
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //修正content的left
            if(child==content){
                if(left>0){
                    left = 0;
                }else if(left<-delete.getMeasuredWidth()){
                    left = -delete.getMeasuredWidth();
                }
            }else if(child==delete){
                //修正delete的left
                if(left>content.getMeasuredWidth()){
                    left = content.getMeasuredWidth();
                }else if(left<(content.getMeasuredWidth()-delete.getMeasuredWidth())){
                    left = (content.getMeasuredWidth()-delete.getMeasuredWidth());
                }
            }

            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //当content移动的时候让delete伴随移动
            if(changedView==content){
                delete.offsetLeftAndRight(dx);
            }else if(changedView==delete){
                //让content进行伴随移动
                content.offsetLeftAndRight(dx);
            }

            //由于offsetLeftAndRight有版本兼容问题，在低版本并没有进行刷新操作，所以需要我们刷新
            invalidate();

            //判断到底是打开还是关闭，从而执行接口的回调方法
            if(content.getLeft()==0){
                if(onSwipeListener!=null){
                    onSwipeListener.onClose(SwipeLayout.this);
                }
            }else if(content.getLeft()==-delete.getMeasuredWidth()){
                if(onSwipeListener!=null){
                    onSwipeListener.onOpen(SwipeLayout.this);
                }
            }

        }
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if(content.getLeft()<-delete.getMeasuredWidth()/2){
                //打开
                openLayout();
            }else {
                //关闭
                closeLayout();
            }

        }
    };

    public void openLayout() {
        dragHelper.smoothSlideViewTo(content,-delete.getMeasuredWidth(),content.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    public void closeLayout() {
        dragHelper.smoothSlideViewTo(content,0,content.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }
    }

    private OnSwipeListener onSwipeListener;

    public OnSwipeListener getOnSwipeListener() {
        return onSwipeListener;
    }
    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    public interface OnSwipeListener{
        void onOpen(SwipeLayout swipeLayout);
        void onClose(SwipeLayout swipeLayout);
    }

    private OnSwipeClickListener clickListener;
    public void setOnSwipeClickListener(OnSwipeClickListener clickListener){
        this.clickListener = clickListener;
    }
    public interface OnSwipeClickListener{
        void onClick();
    }
}

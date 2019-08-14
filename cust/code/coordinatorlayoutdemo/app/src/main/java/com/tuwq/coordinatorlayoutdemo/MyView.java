package com.tuwq.coordinatorlayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MyView extends View {

    private double lastY;
    private double lastX;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 1.获取当前用户手指位置.
        int x =(int) event.getRawX();
        int y =(int) event.getRawY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                // 2.计算两次拖拽的偏移量，并修改当前控件的位置
                int offsetX = (int) (x - lastX);
                int offsetY = (int) (y - lastY);
                CoordinatorLayout.MarginLayoutParams p = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();
                p.leftMargin += offsetX;
                p.topMargin += offsetY;
                setLayoutParams(p);

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        // 2.移动后记录新位置，供下一次移动计算位置使用
        lastX = x;
        lastY = y;
        return true;
    }
}

package com.tuwq.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.w3c.dom.Text;

/**
 * Created by lxj on 2016/11/26.
 */

public class QuickIndexBar extends View {
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    public final int COLOR_DEFAULT = Color.WHITE;
    public final int COLOR_PRESSED = Color.BLACK;
    private Paint paint;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置开启抗锯齿
        paint.setColor(COLOR_DEFAULT);

        int size = getResources().getDimensionPixelSize(R.dimen.text_size);
        paint.setTextSize(size);
        //由于文字绘制默认起点是左下角，可以设置为底边的中心
        paint.setTextAlign(Paint.Align.CENTER);// 基准线 baseline
    }

    float cellHeight;//格子的高度

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //遍历数组绘制26个字母

        for (int i = 0; i < indexArr.length; i++) {
            String text = indexArr[i];
            float x = getMeasuredWidth() / 2;
            //计算y:格子高度一半 + 文字高度一半 +　i*格子高度
            float y = cellHeight / 2 + getTextHeight(text) / 2 + i * cellHeight;

            //如果当前的i等于touchIndex，那么就修改画笔的颜色
            paint.setColor(i==touchIndex?COLOR_PRESSED:COLOR_DEFAULT);

            canvas.drawText(text, x, y, paint);
        }

    }

    int touchIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //用y坐标除以格子的高度得到值就是字母的索引
                int tempIndex = (int) (event.getY() / cellHeight);
                if(touchIndex!=tempIndex){
                    touchIndex = tempIndex;
                    //对touchIndex进行安全性的检查
                    if(touchIndex>=0 && touchIndex<indexArr.length){
                        String s = indexArr[touchIndex];
                        if(listener!=null){
                            listener.onLetterChange(s);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //重置touchIndex
                touchIndex = -1;
                break;
        }
        //重绘
        invalidate();

        return true;
    }

    /**
     * 获取文字的高度
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);//一执行，bounds就有值了
        return bounds.height();
    }

    private OnLetterChangeListener listener;
    public void setOnLetterChangeListener(OnLetterChangeListener listener){
        this.listener = listener;
    }

    public interface OnLetterChangeListener{
        void onLetterChange(String letter);
    }
}

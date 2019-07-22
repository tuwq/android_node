package com.tuwq.togglebutton.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyToggleButton extends View {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.tuwq.togglebutton";
    private Bitmap background;
    private Bitmap icon;
    private int lefticon;
    private int maxDistance;

    /**标示是否抬起鼠标**/
    private boolean isHandUp = false;

    public MyToggleButton(Context context) {
        // super(context);
        this(context, null);
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        // super(context, attrs);
        this(context, attrs, -1);
    }

    public MyToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取自定义属性的值，设置给自定义控件
        int backgroundid = attrs.getAttributeResourceValue(NAMESPACE, "backgrounid", -1);
        int iconid = attrs.getAttributeResourceValue(NAMESPACE, "iconid", -1);
        boolean isToggle = attrs.getAttributeBooleanValue(NAMESPACE, "istoggle", true);
        if (backgroundid != -1 && iconid != -1) {
            setBackgroudAndIcon(backgroundid, iconid);
        }
        setState(isToggle);
    }

    // 1.将开关背景和按钮的图片传递给自定义控件进行使用
    public void setBackgroudAndIcon(int backgroundId, int iconId) {
        // 使用的图片，但是拿到的只是图片的id，所以还需要根据图片的id获取显示图片
        background = BitmapFactory.decodeResource(getResources(), backgroundId);
        icon = BitmapFactory.decodeResource(getResources(), iconId);

        //获取宽度的差值
        maxDistance = background.getWidth() - icon.getWidth();
    }

    // 2.显示自定义控件
    // Android系统中的控件的绘制显示流程：1.测量宽高；2.设置显示位置；3.绘制显示
    // 第一步完成，才会执行第二步，第二步完成，才会执行第三步，只有第三步执行完才会看到控件
    // 如果activity的oncreate方法没有走完，是看不到控件，如果看不到控件，是获取不到控件的信息，比如宽高等信息
    // 1.测量宽高
    // widthMeasureSpec : 控件在布局文件中的宽
    // heightMeasureSpec ：控件在布局文件中的高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 将控件的宽高，设置给系统，由系统根据宽高进行控件的测量操作
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置开关背景图片的宽高设置给自定义控件
        // 自定义测量控件内的内容的宽高
        setMeasuredDimension(background.getWidth(), background.getHeight());

    }

    // 2.设置显示位置,在布局文件中已经设置到界面的中间，不需要再设置位置
    // changed : 表示控件是否有最新的位置
    // left top right bottom : 控件距离父控件左，上，右，下的距离
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    // 3.绘制显示
    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制背景
        // 参数1:绘制的图片
        // 参数2,3:在控件的x和y的那个位置绘制
        // 参数4：画笔,因为图片是现成的图片，是有颜色，宽高的，所以不需要画笔进行操作
        canvas.drawBitmap(background, 0, 0, null);

        //需要控件按钮的绘制的范围，如果超过的范围，不能进行重新绘制
        if (lefticon < 0) {
            lefticon = 0;
        }else if(lefticon > maxDistance){
            lefticon = maxDistance;
        }

        //当抬起鼠标的时候，会执行invalidate方法，会简介的调用onDraw方法，所以在onDraw方法进行回调的设置
        //因为invalidate是在触摸事件的switch之外写的，所以ACTION_MOVE事件也会调用，需要判断是否抬起鼠标
        if (isHandUp) {
            boolean b = lefticon > 0;
            //调用回调方法，将开关状态传递给activity
            if (listener != null) {
                listener.toggleOn(b);
            }
            //数据回传完成，标示一次操作完成，将抬起鼠标标示改为false,为下一次抬起做准备
            isHandUp = false;
        }

        // 绘制按钮
        canvas.drawBitmap(icon, lefticon, 0, null);

        super.onDraw(canvas);
    }

    // 控件触摸事件
    // event : 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新绘制的位置 = 按下的x的坐标 - 按钮的宽度的一半
                lefticon = (int) (event.getX() - icon.getWidth()/2);
                break;
            case MotionEvent.ACTION_MOVE:
                //重新绘制的位置 = 按下的x的坐标 - 按钮的宽度的一半
                lefticon = (int) (event.getX() - icon.getWidth()/2);
                break;
            case MotionEvent.ACTION_UP:
                isHandUp = true;
                //松开鼠标，自动滑动操作
                if (event.getX() < background.getWidth()/2) {
                    lefticon = 0;
                }else{
                    lefticon = maxDistance;
                }
                break;
        }
        //在新的位置绘制的按钮就可以
        //onDraw();//因为不知道系统的画布是如何创建的，所以没有办法直接使用onDraw方法进行绘制
        invalidate();//间接的由系统帮助我们调用了onDraw();

        //True if the event was handled, false otherwise.
        //返回true：表示事件执行，false：表示事件不执行
        return true;
    }



    //回调函数，模仿点击事件进行操作
    private OnToggleOnListener listener;
    public void setOnToggleOnListener(OnToggleOnListener listener){
        this.listener = listener;
    }
    public interface OnToggleOnListener{
        // 传递开关状态给activity
        public void toggleOn(boolean isToggle);
    }

    /**
     * 提供给activity使用的,手动控制开关的状态
     *@param isToggle ： 开关状态
     */
    public void setState(boolean isToggle){

        isHandUp = true;

        if (isToggle) {
            lefticon = maxDistance;
        }else{
            lefticon = 0;
        }
        invalidate();
    }
}

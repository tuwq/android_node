package com.tuwq.googleplay95.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {
    /**
     * 动态生成shape对应的drawable对象
     * @return
     */
    public static GradientDrawable generateDrawable(float radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(ColorUtil.randomBeautifulColor());
        drawable.setCornerRadius(radius);//设置圆角的半径
        return drawable;
    }

    /**
     * 动态生成状态选择器
     * @return
     */
    public static StateListDrawable generateSelector(Drawable pressed, Drawable normal){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},pressed);//设置按下的图片
        drawable.addState(new int[]{},normal);//设置默认的图片

        //设置状态选择器的渐变动画
        drawable.setEnterFadeDuration(500);
        drawable.setExitFadeDuration(500);

        return drawable;
    }
}


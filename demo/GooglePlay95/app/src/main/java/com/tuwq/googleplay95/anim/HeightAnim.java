package com.tuwq.googleplay95.anim;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 更改一个View的高度的动画
 */
public class HeightAnim {
    //值动画器：专门负责帮我们执行一个动画的过程，我们需要指定一个起始值，但是
    //它本身没有动画效果，只是让这2个值进行缓慢的变化
    private ValueAnimator animator;

    public HeightAnim(int startVal, int endVal, final View target){
        animator = ValueAnimator.ofInt(startVal,endVal);
        //我们需要监听值变化的过程，根据当前的值，来实现自己的动画逻辑
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //1.获取动画的值
                int animatedValue = (int) animation.getAnimatedValue();
                //2.强动画的值设置给llSafe的高度
                ViewGroup.LayoutParams params = target.getLayoutParams();
                params.height = animatedValue;
                target.setLayoutParams(params);

                //回调接口的方法
                if(listener!=null){
                    listener.onHeightUpdate(animatedValue);
                }
            }
        });
    }

    /**
     * 开启盖度改变的动画
     * @param duration
     */
    public void start(long duration){
        animator.setDuration(duration);
        animator.start();
    }

    private OnHeightUpdateListener listener;
    public void setOnHeightUpdateListener(OnHeightUpdateListener listener){
        this.listener = listener;
    }
    public interface OnHeightUpdateListener{
        void onHeightUpdate(int animatedValue);
    }
}

package com.tuwq.animationview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "o", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 透明度动画
     * @param v
     */
    public void alpha(View v) {
        /**
         * AlphaAnimation 透明度动画
         * arg1: 动画开始时的透明度
         * arg2: 动画结束时的透明度  1.0完全不透明 0.0透明
         */
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        // 设置动画执行的时间
        animation.setDuration(2000);
        // 设置重复模式,Animation.REVERSE 反向执行
        // Animation.RESTART 重新来一次
        animation.setRepeatMode(Animation.REVERSE);
        // 设置重复的次数,如果为0,那么只执行一次动画
        animation.setRepeatCount(0);
        // 默认false,也就是执行完后恢复到初始状态
        animation.setFillAfter(true);
        // 通过xml获取上面设置
        // animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        // 把当前动画设置到animation上
        iv_image.setAnimation(animation);
        animation.start();
    }

    /**
     * 旋转动画
     * @param v
     */
    public void rotate(View v) {
        /**
         * arg1: 动画开始时 处于的角度
         * arg2: 动画结束时 角度
         */
        RotateAnimation animation = new RotateAnimation(0, 360);
        float fromDegrees = 0;
        float toDegrees = 360;
        // 旋转中心点x坐标的类型
        // RELATIVE_TO_PARENT 相对父容器
        // RELATIVE_TO_SELF  相对自己
        // ABSOLUTE 相对整个布局
        int pivotXType = Animation.RELATIVE_TO_PARENT;
        // 旋转中心点x坐标的值 如果是相对自己或者相对父容器, 可以是一个小数 1.0代表100%
        float pivotXValue = 0.5f;
        // 旋转中心点y坐标的类型
        int pivotYType = Animation.RELATIVE_TO_PARENT; //Ïà¶Ô¸¸ÈÝÆ÷
        // 旋转中心点y坐标的值
        float pivotYValue = 0.5f;
        /**
         * arg1: 动画开始时 处于的角度
         * arg2: 动画结束时 角度
         * arg3: 角度类型,中心
         */
        animation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        // 动画时长
        animation.setDuration(1000);
        // 重复模式
        animation.setRepeatMode(Animation.RESTART);
        // 重复一次
        animation.setRepeatCount(1);
        // 停留到结束状态
        animation.setFillAfter(true);
        animation = (RotateAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        iv_image.setAnimation(animation);
        animation.start();
    }

    /**
     * 缩放动画
     * @param v
     */
    public void scale(View v) {
        /**
         * 缩放动画
         * x方向 从1 -> 2
         * y方向 从1 -> 2
         * 变大了四倍
         */
        ScaleAnimation animation = new ScaleAnimation(1, 2, 1, 2);
        int pivotXType = Animation.RELATIVE_TO_SELF;
        float pivotXValue = 0.5f;
        int pivotYType = Animation.RELATIVE_TO_SELF;
        float pivotYValue = 0.5f;
        /**
         * x方向 从1 -> 2
         * y方向 从1 -> 2
         * x坐标中心点类型,值
         * y坐标中心点类型,值
         */
        animation = new ScaleAnimation(1, 2, 1, 2, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setDuration(1000);
        animation.setRepeatCount(1);
        // 反向变回来
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        animation = (ScaleAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        iv_image.setAnimation(animation);
        animation.start();
    }

    /**
     * 平移动画
     * @param v
     */
    public void translate(View v) {
        /**
         * arg1: x 起始
         * arg2: x 结束
         * arg3: y 起始
         * arg4: y 结束
         */
        TranslateAnimation animation = new TranslateAnimation(10, 100, 10, 100);
        int fromXType = Animation.RELATIVE_TO_PARENT;
        float fromXValue = -0.5f;
        int toXType = Animation.RELATIVE_TO_PARENT;
        float toXValue = 0.5f;
        int fromYType = Animation.RELATIVE_TO_PARENT;
        float fromYValue = -0.5f;
        int toYType = Animation.RELATIVE_TO_PARENT;
        float toYValue  = 0.5f;
        /**
         * arg1: x 起始
         * arg2: x 结束
         * arg3: y 起始
         * arg4: y 结束
         * arg5: x坐标中心点类型
         * arg6: x坐标中心点值
         * arg7: y坐标中心点类型
         * arg8: y坐标中心点值
         */
        animation = new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        animation.setDuration(3000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(1);
        animation.setFillAfter(true);
        // 设置一个动画插入器 通过这个动画插入器 可以在执行的过程中加入 加速 减速 重复 这样的效果
        // 接收参数 Interpolator动画插入器 可以去找系统已经实现好的对象
        animation.setInterpolator(new BounceInterpolator());
        animation = (TranslateAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        iv_image.setAnimation(animation);
        animation.start();

    }

    /**
     * 组合动画
     * @param v
     */
    public void set(View v) {
        /**
         * 组合动画
         * 将各种动画放入set中
         * arg1: 每个动画是否自己的插值器
         */
        AnimationSet animSet = new AnimationSet(false);

        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        animSet.addAnimation(animation);

        float fromDegrees = 0;
        float toDegrees = 360;
        int pivotXType = Animation.RELATIVE_TO_PARENT;
        float pivotXValue = 0.5f;
        int pivotYType = Animation.RELATIVE_TO_PARENT; //Ïà¶Ô¸¸ÈÝÆ÷
        float pivotYValue = 0.5f;
        RotateAnimation ra= new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        ra.setDuration(1000);
        ra.setRepeatMode(Animation.RESTART);
        ra.setRepeatCount(1);
        ra.setFillAfter(true);
        animSet.addAnimation(ra);

        ScaleAnimation sa = new ScaleAnimation(1, 2, 1, 2);
        sa.setDuration(1000);
        sa.setRepeatCount(1);
        sa.setRepeatMode(Animation.REVERSE);
        sa.setFillAfter(true);
        animSet.addAnimation(sa);


        TranslateAnimation ta = new TranslateAnimation(10, 100, 10, 100);
        int fromXType = Animation.RELATIVE_TO_PARENT;
        float fromXValue = -0.5f;
        int toXType = Animation.RELATIVE_TO_PARENT;
        float toXValue = 0.5f;
        int fromYType = Animation.RELATIVE_TO_PARENT;
        float fromYValue = -0.5f;
        int toYType = Animation.RELATIVE_TO_PARENT;
        float toYValue  = 0.5f;
        ta = new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        ta.setDuration(3000);
        ta.setRepeatMode(Animation.RESTART);
        ta.setRepeatCount(1);
        ta.setFillAfter(true);
        ta.setInterpolator(new BounceInterpolator());
        animSet.addAnimation(ta);

        animSet = (AnimationSet) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.set);
        iv_image.setAnimation(animSet);
        animSet.start();
    }

}

package com.tuwq.rotatemenu.tool;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class Tool {

    // 标识动画是否执行: true动画执行,另一个动画不能执行,false动画执行结束另一个动画可以执行
    private static boolean isAnimation = false;

    /**
     * 隐藏菜单操作
     * view: 面向父类进行开发,这样的好处,只要你传递过来的控件是view的子类,就不需要更改参数类型就可以直接实现操作
     */
    public static void hide(View menu, long startOffset) {
        // 旋转动画,旋转角是左上角 0 -180
        setAnimation(menu, 0, -180, 300L);
        // 因为使用的是补间动画实现旋转操作,所以菜单的点击事件和焦点还在原来的位置,所以隐藏菜单的时候,需要将菜单设置为不可用
        setClickAble(menu, false);
    }

    /**
     * 显示菜单操作
     */
    public static void show(View menu) {
        // 旋转动画 -180 0
        setAnimation(menu, -180, 0, 0L);
        // 显示时设置按钮可以点击
        setClickAble(menu, true);
    }

    /**
     * 隐藏显示菜单动画效果
     */
    private static void setAnimation(View menu, float fromDegrees, float toDegrees, long startOffset) {
        /**
         * fromDegrees: 开始旋转的角度
         * toDegrees: 结束的角度
         * pivotXType: 动画执行的参照物
         * pivotXValue: 旋转的中心点x的坐标
         * 0.5宽度 1.0高度正好是中心点
         */
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        rotateAnimation.setDuration(500);
        // 保持动画结束的状态
        rotateAnimation.setFillAfter(true); // 保持动画结束的操作
        rotateAnimation.setStartOffset(startOffset); // 设置动画的开始执行时间(延迟时间)
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimation = true;
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimation = false;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menu.startAnimation(rotateAnimation);
    }

    /**
     * 设置菜单是否可以点击的操作
     * @param menu
     * @param b 是否可用
     */
    private static void setClickAble(View menu, boolean b) {
        menu.setEnabled(b);
        // 因为隐藏显示的时候,除了菜单显示隐藏以外,菜单中的按钮也隐藏显示了,所以除了菜单设置为不可用之外,还要设置菜单中的按钮也是否可用
        // 获取菜单中的按钮
        // instanceof 判断控件是否是viewGroup类型
        if (menu instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) menu;
            // 根据子控件的索引,获取子控件,索引0开始(在布局文件中,控件从上往下,依次从0开始)
            // viewGroup.getChildAt();
            // viewGroup.getChildCount();// 获取菜单中的子控件个数
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                view.setEnabled(b);
            }
        }
    }

    /**
     * 提供给activity使用的,让activity获取动画执行的标识,根据标识判断是否调用一个动画
     * @return
     */
    public static boolean isAnimationStart() {
        return isAnimation;
    }
}

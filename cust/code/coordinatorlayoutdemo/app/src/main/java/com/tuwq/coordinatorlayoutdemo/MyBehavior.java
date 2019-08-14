package com.tuwq.coordinatorlayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class MyBehavior extends CoordinatorLayout.Behavior<Button> {

    private static final String TAG = "MyBehavior";

    public MyBehavior() {
        super();
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 如果返回true则表示 child 将受 dependency 影响
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        Log.d(TAG, "layoutDependsOn: child="+child+";dependency="+dependency.getTag());
//        if (dependency instanceof MyView){
//            return true;
//        }
//        return false;
        return "myview_behavivor".equals(dependency.getTag());
    }


    /**
     * 当 dependency 布局发生变化的时候，onDependentViewChanged 会被调用
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        Log.d(TAG, "onDependentViewChanged: child="+child+";dependency="+dependency);
        int top = dependency.getTop();

        float alpha= top / 300f;

        System.out.println("start");
        System.out.println(alpha);
        System.out.println("end");

        ViewCompat.setAlpha(child,alpha);

        return super.onDependentViewChanged(parent, child, dependency);
    }

}

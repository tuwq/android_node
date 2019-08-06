package com.tuwq.parallax;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView {

    private int originalHeight;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    int maxHeight;//设定为图片本身的高度
    ImageView parallaxImage;
    public void setParallaxImageView(ImageView imageView){
        parallaxImage = imageView;
        //获取ImageView里面src指定图片的原始高度
        maxHeight = parallaxImage.getDrawable().getIntrinsicHeight();

        //获取最初高度，就是135dp
        originalHeight = getResources().getDimensionPixelSize(R.dimen.header_height);

    }

    /**
     * 该方法是在listivew滑动到头继续滑动的时候执行，而且还可以获取到手指滑动的距离
     * @param deltaY    表示手指继续滑动的距离, 正值说明是底部到头，负值是顶部到头
     * @param maxOverScrollY    表示listview到头后还可以继续滑动的最大距离
     * @param isTouchEvent      true表示是用户手指拖动到头，false表示是靠惯性滑动到头(fling)；
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        Log.e("tag"," deltaY: "+ deltaY + "  isTouchEvent:"+ isTouchEvent);

        //效果是发生顶部到头，并且是手指拖动到头
        if(deltaY<0 && isTouchEvent){
            //根据deltaY，让imageView的height增高
            int newHeight = parallaxImage.getHeight() - deltaY/3;
            //对newHeight进行限制
            if(newHeight>maxHeight){
                newHeight = maxHeight;
            }

            ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
            params.height = newHeight;
            parallaxImage.setLayoutParams(params);
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            ValueAnimator animator = ValueAnimator.ofInt(parallaxImage.getHeight(),originalHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    //将value设置imageView的height
                    ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
                    params.height = value;
                    parallaxImage.setLayoutParams(params);
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(600);
            animator.start();

        }
        return super.onTouchEvent(ev);
    }
}

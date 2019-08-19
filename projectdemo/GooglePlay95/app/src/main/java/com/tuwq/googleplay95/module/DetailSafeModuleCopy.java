package com.tuwq.googleplay95.module;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.bean.SafeInfo;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by lxj on 2016/11/21.
 */

public class DetailSafeModuleCopy extends BaseModule<AppInfo> implements View.OnClickListener {

    @Bind(R.id.iv_image1)
    ImageView ivImage1;
    @Bind(R.id.iv_image2)
    ImageView ivImage2;
    @Bind(R.id.iv_image3)
    ImageView ivImage3;
    @Bind(R.id.iv_safe_arrow)
    ImageView ivSafeArrow;
    @Bind(R.id.iv_icon1)
    ImageView ivIcon1;
    @Bind(R.id.tv_des1)
    TextView tvDes1;
    @Bind(R.id.iv_icon2)
    ImageView ivIcon2;
    @Bind(R.id.tv_des2)
    TextView tvDes2;
    @Bind(R.id.iv_icon3)
    ImageView ivIcon3;
    @Bind(R.id.tv_des3)
    TextView tvDes3;
    @Bind(R.id.ll_safe)
    LinearLayout llSafe;

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_safe;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        moduleView.setOnClickListener(this);

        ArrayList<SafeInfo> safeList = appInfo.safe;
        //显示第1个
        SafeInfo safe1 = safeList.get(0);
        tvDes1.setText(safe1.safeDes);
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe1.safeDesUrl, ivIcon1, UILOption.options);
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe1.safeUrl, ivImage1, UILOption.options);

        //显示第2个和第3个需要判断
        if (safeList.size() > 1) {
            SafeInfo safe2 = safeList.get(1);
            tvDes2.setText(safe2.safeDes);
            ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe2.safeDesUrl, ivIcon2, UILOption.options);
            ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe2.safeUrl, ivImage2, UILOption.options);
        } else {
            //说明没有第2个，需要隐藏第2个
            ((ViewGroup) tvDes2.getParent()).setVisibility(View.GONE);
        }

        if (safeList.size() > 2) {
            SafeInfo safe3 = safeList.get(2);
            tvDes3.setText(safe3.safeDes);
            ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe3.safeDesUrl, ivIcon3, UILOption.options);
            ImageLoader.getInstance().displayImage(Url.IMG_PREFIX + safe3.safeUrl, ivImage3, UILOption.options);
        } else {
            //说明没有第3个，需要隐藏第3个
            ((ViewGroup) tvDes3.getParent()).setVisibility(View.GONE);
        }


        //1.先将llSafe的高度设置为0来隐藏
        llSafe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //一般用完立即移除
                llSafe.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //先获取到总高度
                height = llSafe.getHeight();
                //然后将llSafe进行隐藏
                ViewGroup.LayoutParams params = llSafe.getLayoutParams();
                params.height = 0;
                llSafe.setLayoutParams(params);
            }
        });
    }

    private int height;//llSafe的总高度
    boolean isOpen;//是否是展开的
    @Override
    public void onClick(View v) {
        //值动画器：专门负责帮我们执行一个动画的过程，我们需要指定一个起始值，但是
        //它本身没有动画效果，只是让这2个值进行缓慢的变化
        ValueAnimator animator = null;
        if(isOpen){
            //应该关闭
            animator = ValueAnimator.ofInt(height,0);
        }else {
            //应该展开
            animator = ValueAnimator.ofInt(0,height);
            animator.setInterpolator(new OvershootInterpolator());
        }
        //我们需要监听值变化的过程，根据当前的值，来实现自己的动画逻辑
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //1.获取动画的值
                int animatedValue = (int) animation.getAnimatedValue();
                //2.强动画的值设置给llSafe的高度
                ViewGroup.LayoutParams params = llSafe.getLayoutParams();
                params.height = animatedValue;
                llSafe.setLayoutParams(params);
            }
        });
        animator.setDuration(600);
        animator.start();

        //置为反之
        isOpen = !isOpen;
    }
}

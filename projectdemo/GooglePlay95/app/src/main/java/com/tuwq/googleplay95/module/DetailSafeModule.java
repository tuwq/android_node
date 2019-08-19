package com.tuwq.googleplay95.module;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.anim.HeightAnim;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.bean.SafeInfo;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by lxj on 2016/11/21.
 */

public class DetailSafeModule extends BaseModule<AppInfo> implements View.OnClickListener {

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
        // 执行布局结束后
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
    boolean isRotating;//箭头是否正在旋转
    @Override
    public void onClick(View v) {
        //判断当前箭头是否正在旋转
        if(isRotating){
            //就不要执行了
            return;
        }

        HeightAnim heightAnim = null;
        if(isOpen){
            //需要关闭
            heightAnim = new HeightAnim(height,0,llSafe);
        }else {
            //需要打开
            heightAnim = new HeightAnim(0,height,llSafe);
        }
        //开启动画
        heightAnim.start(600);

        //置为反之
        isOpen = !isOpen;

        //让ivSafeArrow旋转180
        ViewCompat.animate(ivSafeArrow)
                .rotationBy(180)
                .setListener(new ViewPropertyAnimatorListenerAdapter(){
                    @Override
                    public void onAnimationStart(View view) {
                        isRotating = true;
                    }
                    @Override
                    public void onAnimationEnd(View view) {
                        isRotating = false;
                    }
                })
                .setDuration(600)
                .start();
    }
}

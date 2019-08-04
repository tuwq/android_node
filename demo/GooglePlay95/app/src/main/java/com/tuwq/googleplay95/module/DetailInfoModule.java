package com.tuwq.googleplay95.module;


import android.support.v4.view.ViewCompat;
import android.text.format.Formatter;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.MyApp;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import butterknife.Bind;

/**
 * 应该负责完成info模块的View的加载和数据的绑定，以及业务逻辑的处理
 */

public class DetailInfoModule extends BaseModule<AppInfo>{

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.rb_star)
    RatingBar rbStar;
    @Bind(R.id.tv_download_num)
    TextView tvDownloadNum;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_size)
    TextView tvSize;
    @Bind(R.id.ll_info)
    LinearLayout llInfo;

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_info;
    }

    /**
     * 绑定数据
     */
    @Override
    public void bindData(AppInfo appInfo){
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+appInfo.iconUrl,ivImage, UILOption.options);
        tvName.setText(appInfo.name);
        rbStar.setRating(appInfo.stars);
        tvDownloadNum.setText("下载："+appInfo.downloadNum);
        tvVersion.setText("版本："+appInfo.version);
        tvDate.setText("日期："+appInfo.date);
        tvSize.setText("大小："+ Formatter.formatFileSize(MyApp.context,appInfo.size));

        //执行掉落动画
        //1.先让llInfo上去
        //添加一个布局完成的监听器
        llInfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当执行完布局之后，回调该方法，因此可以在该方法中获取宽高
             */
            @Override
            public void onGlobalLayout() {
                llInfo.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                llInfo.setTranslationY(-llInfo.getHeight());
                //再通过属性动画移动下来
                ViewCompat.animate(llInfo)
                        .translationY(0)
                        .setDuration(800)
                        .setStartDelay(400)
                        .setInterpolator(new BounceInterpolator())//像球落地一样的感觉
                        .start();
            }
        });
    }

}

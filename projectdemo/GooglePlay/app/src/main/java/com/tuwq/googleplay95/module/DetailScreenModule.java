package com.tuwq.googleplay95.module;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.activity.DetailActivity;
import com.tuwq.googleplay95.activity.ImageScaleActivity;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.MyApp;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.DimenUtil;

import java.util.ArrayList;

import butterknife.Bind;

public class DetailScreenModule extends BaseModule<AppInfo> {
    @Bind(R.id.ll_image)
    LinearLayout llImage;

    DetailActivity activity;
    public void setActivity(DetailActivity activity){
        this.activity = activity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_screen;
    }
    int width,height,margin;
    @Override
    public void bindData(AppInfo appInfo) {
        width = DimenUtil.getDimens(R.dimen.dp90);
        height = DimenUtil.getDimens(R.dimen.dp150);
        margin = DimenUtil.getDimens(R.dimen.dp12);

        final ArrayList<String> screen = appInfo.screen;
        //遍历集合动态创建IMageVIew，添加进来
        for (int i = 0; i < screen.size(); i++) {
            ImageView imageView = new ImageView(MyApp.context);

            //设置宽高以及margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            params.leftMargin = (i==0?0:margin);
            imageView.setLayoutParams(params);

            ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+screen.get(i),imageView, UILOption.options);

            //添加点击事件
            final int finalI = i;//定义临时变量记录i
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ImageScaleActivity.class);
                    intent.putStringArrayListExtra("urlList",screen);
                    intent.putExtra("currentItem", finalI);
                    activity.startActivity(intent);
                }
            });

            llImage.addView(imageView);
        }

    }
}

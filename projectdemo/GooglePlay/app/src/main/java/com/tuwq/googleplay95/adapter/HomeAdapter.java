package com.tuwq.googleplay95.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.MyApp;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAdapter extends MyBaseAdapter<AppInfo> {

    public HomeAdapter(ArrayList<AppInfo> list) {
        super(list);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_home;
    }
    @Override
    protected Object createViewHolder(View convertView, int position) {
        return new HomeHolder(convertView);
    }

    @Override
    protected void bindViewHolder(AppInfo appInfo, Object holder, int position) {
        HomeHolder homeHolder = (HomeHolder) holder;
        homeHolder.tvName.setText(appInfo.name);
        homeHolder.rbStar.setRating(appInfo.stars);
        homeHolder.tvSize.setText(Formatter.formatFileSize(MyApp.context,appInfo.size));
        homeHolder.tvDes.setText(appInfo.des);

        //加载图片
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+appInfo.iconUrl, homeHolder.ivImage,
                UILOption.options);
    }

    static class HomeHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.rb_star)
        RatingBar rbStar;
        @Bind(R.id.tv_size)
        TextView tvSize;
        @Bind(R.id.tv_des)
        TextView tvDes;

        HomeHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

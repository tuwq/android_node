package com.tuwq.googleplay95.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

public class HomePagerAdapter extends BasePagerAdapter {
    public HomePagerAdapter(ArrayList<String> urlList) {
        super(urlList);
    }

    @Override
    public int getCount() {
        return urlList.size()*1000000;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        //设置ImageView的宽高可以铺满四边
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //加载图片
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+urlList.get(position%urlList.size())
                ,imageView, UILOption.options);

        container.addView(imageView);
        return imageView;
    }
}

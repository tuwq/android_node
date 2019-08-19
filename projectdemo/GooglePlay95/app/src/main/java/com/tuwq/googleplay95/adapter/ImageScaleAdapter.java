package com.tuwq.googleplay95.adapter;

import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.global.MyApp;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;


public class ImageScaleAdapter extends BasePagerAdapter {
    public ImageScaleAdapter(ArrayList<String> urlList) {
        super(urlList);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(MyApp.context);

        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+urlList.get(position),imageView, UILOption.options);

        container.addView(imageView);
        return imageView;
    }
}

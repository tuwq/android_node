package com.tuwq.googleplay95.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.ImageScaleAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageScaleActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        ButterKnife.bind(this);

        //1.接收数据
        ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
        int currentItem = getIntent().getIntExtra("currentItem",0);

        ImageScaleAdapter adapter = new ImageScaleAdapter(urlList);
        viewPager.setAdapter(adapter);

        //设置默认显示第几张
        viewPager.setCurrentItem(currentItem);
    }
}

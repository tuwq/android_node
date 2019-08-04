package com.tuwq.googleplay95.adapter;

import android.view.View;

import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;

import java.util.ArrayList;

public class AppAdapter extends MyBaseAdapter<AppInfo> {
    public AppAdapter(ArrayList<AppInfo> list) {
        super(list);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_home;
    }

    @Override
    protected Object createViewHolder(View convertView, int position) {
        return new HomeAdapter.HomeHolder(convertView);
    }

    @Override
    protected void bindViewHolder(AppInfo appInfo, Object holder, int position) {

    }
}


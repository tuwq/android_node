package com.tuwq.startfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 把fragment对应的xml布局文件 转换为view对象 加载到界面上
        View view = inflater.inflate(R.layout.fragment_first, null);
        return view;
    }
}

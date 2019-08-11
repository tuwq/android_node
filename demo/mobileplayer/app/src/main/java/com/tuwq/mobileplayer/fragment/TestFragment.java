package com.tuwq.mobileplayer.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuwq.mobileplayer.R;

public class TestFragment extends Fragment {
    /**
     * 获取Fragment对象
     * @return
     */
    public static TestFragment newInstance(String content) {
        // 填充初始化参数
        Bundle args = new Bundle();
        args.putString("content",content);

        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(args);

        return testFragment;
    }

    /**
     * 创建view
     * @param inflater 创建view的对象
     * @param container viewGroup
     * @param savedInstanceState 实例状态
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,null);
        TextView tv_test = (TextView) view.findViewById(R.id.tv_test);

        // 获取初始化参数
        Bundle args = getArguments();
        String content = args.getString("content");
        tv_test.setText(content);
        return view;
    }
}

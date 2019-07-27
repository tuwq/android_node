package com.tuwq.zhbj.fragment;

import android.view.View;
import android.widget.TextView;

import com.tuwq.zhbj.base.BaseFragment;

/**
 * 首页的fragment
 * 因为HomeFragment和MenuFragment都需要加载布局，显示数据，相同操作，抽取到父类
 */
public class HomeFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(activity);
        textView.setText("我是首页的fragment");
        return textView;
    }
    @Override
    public void initData() {

    }
}

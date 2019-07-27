package com.tuwq.zhbj.fragment;

import android.view.View;
import android.widget.TextView;

import com.tuwq.zhbj.base.BaseFragment;

/**
 * 菜单页的fragment
 */
public class MenuFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(activity);
        textView.setText("我是菜单页的fragment");
        return textView;
    }
    @Override
    public void initData() {

    }
}

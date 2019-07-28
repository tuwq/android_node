package com.tuwq.zhbj.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tuwq.zhbj.R;
import com.tuwq.zhbj.base.BaseFragment;

import java.util.List;

/**
 * 菜单页的fragment
 */
public class MenuFragment extends BaseFragment {

    @Override
    public View initView() {
        this.view = View.inflate(activity, R.layout.menufragment, null);
        return this.view;
    }
    @Override
    public void initData() {

    }
}

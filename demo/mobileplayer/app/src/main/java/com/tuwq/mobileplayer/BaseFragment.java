package com.tuwq.mobileplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuwq.mobileplayer.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * 基类fragment
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;

    /**
     * 创建并初始化子类
     * @param inflater 创建view的对象
     * @param container viewGroup
     * @param savedInstanceState 实例状态
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e(this.getClass(), "BaseFragment.onCreateView,rootView="+rootView);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null);
        }
        LogUtils.e(this.getClass(), "BaseFragment.onCreateView,rootView="+rootView);
        ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

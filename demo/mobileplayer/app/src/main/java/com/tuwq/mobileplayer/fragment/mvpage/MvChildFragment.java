package com.tuwq.mobileplayer.fragment.mvpage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.adapter.MvChildAdapter;
import com.tuwq.mobileplayer.bean.VideoBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MvChildFragment extends BaseFragment implements MvChildMvp.View {
    private static final String TAG = "MvChildFragment";

    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private String code;
    private MvChildMvp.Presenter presenter;
    private MvChildAdapter mvChildAdapter;
    private List<VideoBean> videoList;
    private boolean isRefresh;
    private boolean hasMore = true;

    public static MvChildFragment newInstance(String code){
        Bundle args = new Bundle();
        args.putString("code",code);

        MvChildFragment fragment = new MvChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        // 获取要显示的地区类型
        Bundle arguments = getArguments();
        String code = arguments.getString("code");

        // 使用 presenter 加载数据
        presenter = new MvChildPresenter(this);
        presenter.loadData(code,offset,SIZE);

        // 初始化列表
        recylerview.setLayoutManager(new LinearLayoutManager(getContext()));
        videoList = new ArrayList<>();
        mvChildAdapter = new MvChildAdapter(videoList);
        recylerview.setAdapter(mvChildAdapter);
        // 上拉加载监听
        recylerview.addOnScrollListener(new OnMvScrollListener());

        // 下拉刷新监听
        refresh.setOnRefreshListener(new OnMvRefreshListener());
    }

    @Override
    public void setData(List<VideoBean> videos) {
        LogUtils.e(TAG,"MvChildFragment.setData,videos="+videos.size());
        if (isRefresh){
            videoList.clear();
            isRefresh = false;
            refresh.setRefreshing(false);// 隐藏刷新提示
        }

        // 计算下一次加载数据的起始位置
        offset += videos.size();
        hasMore = videos.size() >= SIZE;

        videoList.addAll(videos);
        mvChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int code, Exception e) {
        LogUtils.e(TAG, "加载数据失败" + videoList.size());
        if (isRefresh) {
            this.videoList.clear();
            isRefresh = false;
            // 将刷新圈隐藏
            refresh.setRefreshing(false);
        }
    }

    private class OnMvRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            isRefresh = true;
            offset = 0;
            presenter.loadData(code,offset,SIZE);
        }
    }

    private class OnMvScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // 获取当前可见的最后一个条目位置
            LinearLayoutManager layoutManager = (LinearLayoutManager) recylerview.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

            // 当用户松手时发现已经是在最后一项，则获取下一页数据
            if (newState== 0&& lastVisibleItemPosition == videoList.size()-1 && hasMore){
                presenter.loadData(code,offset,SIZE);
            }
        }
    }
}

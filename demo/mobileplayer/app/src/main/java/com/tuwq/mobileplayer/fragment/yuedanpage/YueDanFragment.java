package com.tuwq.mobileplayer.fragment.yuedanpage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.adapter.YueDanAdapter;
import com.tuwq.mobileplayer.bean.YueDanBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class YueDanFragment extends BaseFragment implements YueDanMvp.View {
    private static final String TAG = "YueDanFragment";

    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private YueDanMvp.Presenter presenter;
    private List<YueDanBean.PlayListsBean> list;
    private YueDanAdapter yueDanAdapter;
    private boolean isRefresh;
    private boolean hasMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        // 使用 Presenter 加载数据
        presenter = new YueDanPresenter(this);
        presenter.loadData(offset,SIZE);

        // 从初始化 RecyclerView
        recylerview.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        yueDanAdapter = new YueDanAdapter(list);
        recylerview.setAdapter(yueDanAdapter);
        // 上拉加载的监听
        recylerview.addOnScrollListener(new OnYueDanScrollListener());

        // 下拉刷新的监听
        refresh.setOnRefreshListener(new OnYueDanRefreshListener());
    }

    @Override
    public void setData(List<YueDanBean.PlayListsBean> playLists) {
        LogUtils.e(TAG,"YueDanFragment.setData,playLists="+playLists.size());
        if (isRefresh){
            // 针对下拉刷新的处理
            list.clear();
            isRefresh = false;
            refresh.setRefreshing(false);
        }

        offset += playLists.size();// 计算下一页的起始位置
        hasMore = playLists.size() == SIZE;

        list.addAll(playLists);
        yueDanAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(int code, Exception e) {
        LogUtils.e(TAG, "加载数据失败" + list.size());
        if (isRefresh) {
            this.list.clear();
            isRefresh = false;
            // 将刷新圈隐藏
            refresh.setRefreshing(false);
        }
    }

    private class OnYueDanRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            isRefresh = true;
            offset = 0;
            presenter.loadData(offset,SIZE);
        }
    }

    private class OnYueDanScrollListener extends RecyclerView.OnScrollListener {
        @Override
        // 滚动状态发生变化
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // 获取当前可见的最后一个条目的位置
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recylerview.getLayoutManager();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

            // 判断是否要获取下一页的数据
            if (newState == 0 && lastVisibleItemPosition == list.size() - 1 && hasMore){
                presenter.loadData(offset,SIZE);
            }
        }
    }
}

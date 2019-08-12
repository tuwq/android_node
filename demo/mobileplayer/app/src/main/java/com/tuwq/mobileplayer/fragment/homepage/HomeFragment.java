package com.tuwq.mobileplayer.fragment.homepage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.adapter.HomeAdapter;
import com.tuwq.mobileplayer.bean.VideoBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class HomeFragment extends BaseFragment implements HomeMvp.View {

    private static final String TAG = "HomeFragment";

    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private HomeMvp.Presenter presenter;
    private List<VideoBean> videoBeen;
    private HomeAdapter homeAdapter;
    private boolean isRefresh;
    private boolean hasMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 创建view,填充数据
     */
    @Override
    protected void initView() {
        // TextView tv_test = (TextView) rootView.findViewById(R.id.tv_test);
        // 获取初始化参数
        // tv_test.setText("这是 首页 的界面");
        // 创建 presenter
        LogUtils.e(TAG, "HomeFragment.initView,创建 presenter,并请求数据");
        presenter = new HomePresenter(this);
        presenter.loadData(offset, SIZE);

        // 填充 RecylerView 列表
        // 布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        // GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);// 九宫格
        // StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);//瀑布流
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerview.setLayoutManager(layoutManager);
        // 设置Adapter
        videoBeen = new ArrayList<>();
        homeAdapter = new HomeAdapter(videoBeen);
        recylerview.setAdapter(homeAdapter);
        // 上拉加载滚动监听
        recylerview.addOnScrollListener(new OnMainScrollListener());

        // 下拉刷新监听
        refreshLayout.setOnRefreshListener(new OnMainRefreshListener());
    }


    /**
     * 获得到了数据
     * @param videoBeen 数据
     */
    @Override
    public void setData(List<VideoBean> videoBeen) {
        LogUtils.e(TAG, "HomeFragment.setData,videoBeen=" + videoBeen.size());
        if (isRefresh) {
            this.videoBeen.clear();
            isRefresh = false;
            // 将刷新圈隐藏
            refreshLayout.setRefreshing(false);
        }
        // 增加页数的起始点
        offset += videoBeen.size();
        // 如果返回的数据量不等于请求的大小，则说明没有下一页了,没有更多的数据了
        hasMore = videoBeen.size() == SIZE;

        this.videoBeen.addAll(videoBeen);
        // 刷新数据
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int code, Exception e) {
        LogUtils.e(TAG, "加载数据失败" + videoBeen.size());
        if (isRefresh) {
            this.videoBeen.clear();
            isRefresh = false;
            // 将刷新圈隐藏
            refreshLayout.setRefreshing(false);
        }
    }

    /**
     * SwipeRefreshLayout的下拉刷新监听
     */
    private class OnMainRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        /**
         * 刷新操作手势执行后
         */
        @Override
        public void onRefresh() {
            offset = 0;
            presenter.loadData(offset, SIZE);
            isRefresh = true;
        }
    }

    /**
     * RecyclerView的上拉加载滚动监听
     */
    private class OnMainScrollListener extends RecyclerView.OnScrollListener {
        /**
         * 当滚动状态发生变化的时候被调用
         * @param recyclerView
         * @param newState 0 说明是 静止状态，为 1 说明是开始滚动，为 2 说明是开始惯性滚动
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // 获取当前可见的最后一个条目位置
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            if (newState == 0 && lastVisibleItemPosition == videoBeen.size() -1 && hasMore){
                // 如果状态变为 静止， 则准备加载下一页数据
                presenter.loadData(offset,SIZE);
            }
        }

        /**
         * 不断的获取滚动的位置
         * @param recyclerView
         * @param dx
         * @param dy
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LogUtils.e(TAG,"OnMainScrollListener.onScrolled," + dx + "." + dy);
        }
    }
}
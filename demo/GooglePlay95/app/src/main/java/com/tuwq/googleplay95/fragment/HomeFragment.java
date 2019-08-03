package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.HomeAdapter;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.bean.Home;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    ArrayList<AppInfo> list = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private PullToRefreshListView ptrView;

    public View getSuccessView() {
        ptrView = (PullToRefreshListView) View.inflate(getContext(), R.layout.ptr_listview, null);
        //1.设置刷新模式，因为它默认只能下拉刷新
        ptrView.setMode(PullToRefreshBase.Mode.BOTH);//设置可以下拉以及上啦加载更多
        //2.设置刷新监听器
        ptrView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            /**
             * 下拉刷新和加载更多都会执行
             * @param refreshView
             */
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        //3.设置数据
        ListView listView = ptrView.getRefreshableView();
        //去掉listview自带的divider
        listView.setDividerHeight(0);
        //去掉listview自带的selector
        listView.setSelector(android.R.color.transparent);

        //设置数据适配器
        homeAdapter = new HomeAdapter(list);
        listView.setAdapter(homeAdapter);

        return ptrView;
    }

    public void loadData() {
        //如果是下拉刷新，则清空集合,重新加载第0页的数据
        if(ptrView.getCurrentMode()== PullToRefreshBase.Mode.PULL_FROM_START){
            //清空集合或者获取最新的新闻
            list.clear();
        }

        //加载数据的代码
        HttpHelper.create()
        .get(Url.Home + list.size(), new HttpHelper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                //显示成功界面
                stateLayout.showSuccessView();

                Home home = GsonUtil.parseJsonToBean(result, Home.class);
                if(home!=null){
                    //添加数据
                    list.addAll(home.list);
                    homeAdapter.notifyDataSetChanged();
                }
                //完成刷新
                ptrView.onRefreshComplete();
            }
            @Override
            public void onFail(Exception e) {

            }
        });
    }
}

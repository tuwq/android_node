package com.tuwq.googleplay95.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.HomeAdapter;
import com.tuwq.googleplay95.adapter.HomePagerAdapter;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.bean.Home;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;

public class HomeFragmentCopy extends BaseFragment {
    ArrayList<AppInfo> list = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private PullToRefreshListView ptrView;
    private ListView listView;
    private ViewPager viewPager;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //先让ViewPager选中下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            //接着发
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };

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
        listView = ptrView.getRefreshableView();
        //去掉listview自带的divider
        listView.setDividerHeight(0);
        //去掉listview自带的selector
        listView.setSelector(android.R.color.transparent);

        //添加headerView
        addHeaderView();


        //设置数据适配器
        homeAdapter = new HomeAdapter(list);
        listView.setAdapter(homeAdapter);

        return ptrView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //发送一个延时消息
        handler.sendEmptyMessageDelayed(0,2000);

    }

    @Override
    public void onStop() {
        super.onStop();
        //停止自动轮播的行为
        handler.removeMessages(0);
    }

    /**
     * 添加headerView
     */
    private void addHeaderView() {
        View headerView = View.inflate(getContext(),R.layout.layout_home_header,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.viewPager);
        listView.addHeaderView(headerView);
    }

    public void loadData() {
        //如果是下拉刷新，则清空集合,重新加载第0页的数据
        if(ptrView.getCurrentMode()== PullToRefreshBase.Mode.PULL_FROM_START){
            //清空集合
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
                            //给ViewPager设置adapter
                            if(home.picture!=null && home.picture.size()>0){
                                viewPager.setAdapter(new HomePagerAdapter(home.picture));
                            }

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

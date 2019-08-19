package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.MyBaseAdapter;
import com.tuwq.googleplay95.http.HttpHelper;

import java.util.ArrayList;

/**
 * 带有下拉刷新的列表的Fragment的基类
 * @param <T>
 */
public abstract class PtrListFragment<T> extends BaseFragment implements AdapterView.OnItemClickListener{

    PullToRefreshListView ptrView;
    ListView listView;

    ArrayList<T> list = new ArrayList<>();
    MyBaseAdapter<T> baseAdapter;

    @Override
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

        //addHeaderView
        addHeaderView();

        //4.设置数据适配器
        baseAdapter = getAdapter();
        listView.setAdapter(baseAdapter);

        //5.设置item的点击监听器
        listView.setOnItemClickListener(this);

        return ptrView;
    }

    /**
     * 让子类进行扩展，如果他们有添加头部的需要，那么就是实现
     */
    protected void addHeaderView() {}
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void loadData() {
        //处理下拉刷新的逻辑问题
        //如果是下拉刷新，则清空集合,重新加载第0页的数据
        if(ptrView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START){
            //清空集合
            list.clear();
        }
        //加载数据的代码
        HttpHelper.create()
            .get(getUrl(), new HttpHelper.HttpCallback() {
                @Override
                public void onSuccess(String result) {
                    //显示成功界面
                    stateLayout.showSuccessView();

                    //解析数据，更新UI
                    parseDataAndUpdate(result);

                    //完成刷新
                    ptrView.onRefreshComplete();

                }
                @Override
                public void onFail(Exception e) {
                    //如果请求失败，并且当前集合中木有数据，那么直接显示失败的View
                    if(list.size()==0){
                        stateLayout.showErrorView();
                    }
                }
            });
    }

    /**
     * 获取一个Adapter对象，由每个子类实现
     * @return
     */
    public abstract MyBaseAdapter<T> getAdapter();

    /**
     * 获取请求的url地址
     * @return
     */
    public abstract String getUrl() ;

    /**
     * 让子类来实现解析json数据，和更新UI的，每个子类的实现是不一样滴
     * @param result
     */
    protected abstract void parseDataAndUpdate(String result);
}

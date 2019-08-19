package com.tuwq.googleplay95.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.activity.DetailActivity;
import com.tuwq.googleplay95.adapter.HomeAdapter;
import com.tuwq.googleplay95.adapter.HomePagerAdapter;
import com.tuwq.googleplay95.adapter.MyBaseAdapter;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.bean.Home;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;

public class HomeFragment extends PtrListFragment<AppInfo> {

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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //开启详情界面
        Intent intent = new Intent(getActivity(), DetailActivity.class);
//        LogUtil.e("position: "+position);
        intent.putExtra("packageName",list.get(position-2).packageName);
        startActivity(intent);
    }

    /**
     * 添加headerView
     */
    @Override
    protected void addHeaderView() {
        View headerView = View.inflate(getContext(),R.layout.layout_home_header,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.viewPager);
        listView.addHeaderView(headerView);
    }
    @Override
    public MyBaseAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);
    }
    @Override
    public String getUrl() {
        return Url.Home + list.size();
    }
    @Override
    protected void parseDataAndUpdate(String result) {
        Home home = GsonUtil.parseJsonToBean(result, Home.class);
        if(home!=null){
            //给ViewPager设置adapter
            if(home.picture!=null && home.picture.size()>0){
                viewPager.setAdapter(new HomePagerAdapter(home.picture));
            }

            //添加数据
            list.addAll(home.list);
            baseAdapter.notifyDataSetChanged();
        }
    }
}

package com.tuwq.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuwq.zhbj.R;
import com.tuwq.zhbj.base.BaseFragment;
import com.tuwq.zhbj.base.BasePager;
import com.tuwq.zhbj.pager.GovPager;
import com.tuwq.zhbj.pager.HomePager;
import com.tuwq.zhbj.pager.NewsCenterPager;
import com.tuwq.zhbj.pager.SettingPager;
import com.tuwq.zhbj.pager.SmartServicePager;

import java.util.ArrayList;

/**
 * 首页的fragment
 * 因为HomeFragment和MenuFragment都需要加载布局，显示数据，相同操作，抽取到父类
 */
public class HomeFragment extends BaseFragment {
    @Override
    public View initView() {
        this.view = View.inflate(activity, R.layout.homefragment, null);
        return this.view;
    }
    ViewPager mViewPager;
    ArrayList<BasePager> list;
    MyAdapter myAdapter;

    @Override
    public void initData() {
        // 将首页,新闻中心等界面,添加到viewPager中进行展示操作
        mViewPager = view.findViewById(R.id.homefragment_vp_viewpager);
        // 创建首页,新闻中心界面,保存到list集合中,方便viewPager的adapter使用
        list = new ArrayList<BasePager>();
        list.clear();// 保证每次保存都是最新界面
        list.add(new HomePager(this.activity));
        list.add(new NewsCenterPager(this.activity));
        list.add(new SmartServicePager(this.activity));
        list.add(new GovPager(this.activity));
        list.add(new SettingPager(this.activity));

        // 设置viewPager的adapter显示数据
        if (myAdapter == null) {
            myAdapter = new MyAdapter();
            mViewPager.setAdapter(myAdapter);
        } else {
            myAdapter.notifyDataSetChanged();
        }
        // 监听viewPager界面切换监听,当切换到相应的界面时候,加载相应界面数据,刚进入界面时候
        // 没有进行界面的切换
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                // 切换到哪个界面,加载哪个界面的数据
                // 获取切换到的界面
                BasePager basePager = list.get(i);
                basePager.initData();
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        list.get(0).initData();
    }

    /**
     * viewPager的PageAdapter
     */
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = list.get(position);
            // viewpager只能存放view对象
            View rootview = pager.view;
            container.addView(rootview);
            return rootview;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
}

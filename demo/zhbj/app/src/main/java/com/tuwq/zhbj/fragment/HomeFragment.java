package com.tuwq.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
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
    private RadioGroup mButtons;

    @Override
    public void initData() {
        // 将首页,新闻中心等界面,添加到viewPager中进行展示操作
        mViewPager = view.findViewById(R.id.homefragment_vp_viewpager);
        mButtons = (RadioGroup) view.findViewById(R.id.homefragment_rg_buttons);

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
            public void onPageSelected(int position) {
                // 切换到哪个界面,加载哪个界面的数据
                // 获取切换到的界面
                BasePager basePager = list.get(position);
                basePager.initData();

                //6.判断当切换到首页和设置页面的时候，不能进行侧拉菜单侧拉，当切换到其他三个界面的时候可以进行侧拉菜单侧拉
                if (position == 0 || position == list.size()-1) {
                    //不能进行侧拉菜单侧拉
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }else{
                    //可以进行侧拉菜单侧拉
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        list.get(0).initData();

        // 5.点击相应的RadioButton实现界面切换
        //设置默认选中首页的Radiobutton
        mButtons.check(R.id.homefragment_rbtn_shou);//设置选中哪个Radiobutton
        // 监听Radiogroup中的Radiobutton选中操作
        mButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // 当Radiobutton选中的时候调用的方法
            // checkedId : 选中的Radiobutton的id
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 根据选中的Radiobutton的id设置viewpager切换到那个界面
                switch (checkedId) {
                    case R.id.homefragment_rbtn_shou:
                        //首页
                        //设置当前显示的界面是首页的界面
                        //mViewPager.setCurrentItem(0);//设置当前显示的界面，item：界面的索引
                        mViewPager.setCurrentItem(0, false);//设置当前显示的界面，同时设置切换界面的时候是否执行界面切换的动画效果，true:执行，false:不执行
                        break;
                    case R.id.homefragment_rbtn_newscenter:
                        //新闻中心
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.homefragment_rbtn_smartservice:
                        //智慧服务
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.homefragment_rbtn_gov:
                        //政务
                        mViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.homefragment_rbtn_setting:
                        //设置
                        mViewPager.setCurrentItem(4, false);
                        break;
                }
            }
        });
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

    /**
     * 获取NewsCenterPager界面对象的
     *@return
     */
    public NewsCenterPager getNewsCenterpager(){
        return (NewsCenterPager) list.get(1);
    }
}

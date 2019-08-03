package com.tuwq.googleplay95.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gxz.PagerSlidingTabStrip;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.MainAdapter;
import com.tuwq.googleplay95.fragment.AppFragment;
import com.tuwq.googleplay95.fragment.CategoryFragment;
import com.tuwq.googleplay95.fragment.GameFragment;
import com.tuwq.googleplay95.fragment.HomeFragment;
import com.tuwq.googleplay95.fragment.HotFragment;
import com.tuwq.googleplay95.fragment.RecommendFragment;
import com.tuwq.googleplay95.fragment.SubjectFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.slidingTab)
    PagerSlidingTabStrip slidingTab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setActionBar();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AppFragment());
        fragments.add(new GameFragment());
        fragments.add(new SubjectFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new HotFragment());

        String[] titles = getResources().getStringArray(R.array.tab_names);
        // 给ViewPager填充数据
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments,titles);
        viewPager.setAdapter(mainAdapter);

        // 让ViewPager和指示器绑定
        slidingTab.setViewPager(viewPager);
        // 设置Tab Indicator的颜色
        slidingTab.setIndicatorColor(getResources().getColor(R.color.color_45c01a));
        // 设置Tab 指示器Indicator的高度,传入的是dp
        slidingTab.setIndicatorHeight(4);
        slidingTab.setShouldExpand(false);

        // sp,db,getResource

    }

    /**
     * 设置ActionBar
     */
    private void setActionBar() {
        // 获取ActionBar对象
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(this.getResources().getString(R.string.app_name)); // 修改标题
        // 设置图标的,但是该方法高版本无效,低版本才有效
        // actionBar.setIcon(R.mipmap.ic_launcher);

        // 让actionBar可以点击
        actionBar.setDisplayShowHomeEnabled(true);// 让home按钮可以点击
        actionBar.setDisplayHomeAsUpEnabled(true);// 显示home按钮

        // 让ActionBar的home按钮变身为汉堡包按钮
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                0, 0);
        drawerToggle.syncState();// 变身为汉堡包

        // 在菜单滑动过程中让汉堡包按钮执行动画
        drawerLayout.addDrawerListener(drawerToggle);//3.让ActionBar的home按钮变身为汉堡包按钮
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerToggle.syncState();//变身为汉堡包

        //4.在菜单滑动过程中让汉堡包按钮执行动画
        drawerLayout.addDrawerListener(drawerToggle);
    }

    /**
     * 当汉堡包被点击时执行的方法
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 如果 drawerLayout已经打开,则关闭
        /*if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            // 需要关闭
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            // 需要打开
            drawerLayout.openDrawer(Gravity.LEFT);
        }*/
        // drawerToggle内部封装了对菜单打开与关闭的判断,我们只需要调用即可
        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}

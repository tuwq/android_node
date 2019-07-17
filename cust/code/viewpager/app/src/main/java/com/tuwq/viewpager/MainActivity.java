package com.tuwq.viewpager;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    ViewPager viewPager;
    private int[] imageResIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };
    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };
    // 展示图片的imageView
    private ImageView[] imageViews = new ImageView[imageResIds.length];
    private TextView mViewPagerText;
    // 保存创建的点的操作
    private View[] dots = new View[imageResIds.length];
    private LinearLayout mLLRootDot;
    // 当前选中的点
    private View currentselectview;
    int maxpage;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            //实现切换界面操作
            switchPager();
        };
    };

    /**
     * 切换viewpager界面
     */
    private void switchPager() {
        //0 -> 1  -> 2 -> 3 -> 4 -> 5
        int currentItem = viewPager.getCurrentItem();//获取当前显示的界面的索引
        currentItem++;
        viewPager.setCurrentItem(currentItem);//设置显示当前界面
        //执行下一次的切换
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        viewPager = this.findViewById(R.id.viewpager);
        mViewPagerText = this.findViewById(R.id.viewpager_text);
        mLLRootDot = (LinearLayout) findViewById(R.id.root_dot);

        // 根据图片的张数创建相应个数的imageView,并将图片设置给相应imageView显示
        for (int i = 0; i < imageResIds.length; i++) {
            createImageView(i);
            createDot(i);
        }
        // 当viewPager的界面切换的时候,更改显示文本
        // 设置viewPager的界面切换监听,当刚进入界面的时候不会调用,所以需要单独设置第一个界面的文本
        viewPager.setOnPageChangeListener(listener);
        change(0); // 设置viewPager第一个界面文本

        // 获取viewpager无限滑动的最大条目数
        maxpage = imageResIds.length * 1000 * 100;
        // 设置往前也可以实现无限滑动,取条目数的中间数
        int currentItem = maxpage / 2;

        // 跟listView相似
        viewPager.setAdapter(new MyAdapter()); // 设置viewPager的adapter

        viewPager.setCurrentItem(currentItem);// 设置viewPager当前显示的条目,item: 条目的索引

        // 发送一个定时消息,执行代码,会在2秒之后给handler
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 创建点
     * @param i
     */
    private void createDot(int i) {
        // 将创建的点保存到数组中方便操作
        dots[i] = new View(this);
        dots[i].setBackgroundResource(R.drawable.selector_dot);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
        // 跟布局文件中的android:layout_marginRight属性的含义一样的,设置距离右边的操作
        params.rightMargin = 5;
        // 将属性设置给点生效
        dots[i].setLayoutParams(params);// 设置属性给view对象
        // 将创建的点显示在界面上
        mLLRootDot.addView(dots[i]);
    }

    /**
     * viewPager的界面切换监听
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        /**
         * 当viewPager滑动的时候调用的方法
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        /**
         * 当界面切换完成切换的方法
         * @param position 切换完成的界面的索引
         */
        @Override
        public void onPageSelected(int position) {
            // 设置显示相应界面的文本
            change(position);
        }
        /**
         * 当滑动状态改变的时候调用的方法
         * @param state viewpager的切换状态
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            // ViewPager.SCROLL_STATE_IDLE//空闲的状态，停止滑动的状态
            // ViewPager.SCROLL_STATE_DRAGGING;//触摸滑动的状态
            // ViewPager.SCROLL_STATE_SETTLING //滑动到最后一个条目的状态
            // 当手动滑动的时候不能进行自动滑动，当不滑动的时候重新进行自动滑动
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                handler.sendEmptyMessageDelayed(0, 2000);
            }else{
                handler.removeMessages(0);
            }
        }
    };

    /**
     * 设置切换的文本和点
     * @param position 切换完成的界面的索引
     */
    protected void change(int position) {
        // 通过求余的形式获得显示文本和点的索引
        position = position % imageResIds.length;
        mViewPagerText.setText(descs[position]);
        if (currentselectview != null) {
            currentselectview.setSelected(false);
        }
        dots[position].setSelected(true); // 表示选中控件
        // 当当前的点变成白色的时候,上一个点要变成黑色
        currentselectview = dots[position];
    }

    /**
     * 创建imageView
     * @param i
     */
    private void createImageView(int i) {
        // 将构建的imageView保存到数组中,方便后面使用
        imageViews[i] = new ImageView(this);
        imageViews[i].setBackgroundResource(imageResIds[i]);
    }

    private class MyAdapter extends PagerAdapter {
        // 设置viewPager的条目的个数,跟listView的adapter的getCount方法是一致的
        @Override
        public int getCount() {
            return maxpage;
        }

        /**
         * 设置是否显示相应界面的操作,因为有左右两种切换操作,所以需要知道相应的切换操作应该切换到哪个界面
         * @param view 显示的界面
         * @param o 创建的显示的界面,也是添加到viewpager中实际显示的界面,instantiateItem返回的
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 给viewPager添加显示界面,在instantiateItem方法中就需要将显示的界面创建出来,并设置给viewPager
         * @param container viewPager
         * @param position 条目索引
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // 通过求余的形式获取图片的索引
            position = position % imageResIds.length;
            // 根据条目的索引获取相应的imageView
            ImageView imageView = imageViews[position];
            container.addView(imageView);// 将imageView添加给viewPager
            // 添加什么view对象,返回什么view对象
            return imageView;
        }

        /**
         * 当界面删除时调用
         * @param container viewPager
         * @param position 条目的索引
         * @param object instantiateItem返回的对象
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}

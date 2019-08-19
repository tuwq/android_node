package com.tuwq.pulltorefreshlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.tuwq.pulltorefreshlistview.R;

public class PullToRefreshListView extends ListView implements OnScrollListener {

    public PullToRefreshListView(Context context) {
        this(context,null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);

        addHeader();
        addFooter();
        this.setOnScrollListener(this);
    }

    private View headerView;
    private int headermeasuredHeight;
    private RotateAnimation up;
    private RotateAnimation down;
    // 按下时的y坐标
    private int downY;

    /** 下拉刷新的状态 **/
    private final int PULL_DOWN = 1;
    /** 松开刷新的状态 **/
    private final int REFRESH = 2;
    /** 正在刷新的操作 **/
    private final int REFRESHING = 3;
    /** 当前的状态,默认下拉刷新状态 **/
    private int CURRENTSTATE = PULL_DOWN;

    // 相关组件
    ImageView mArrow;
    ProgressBar mPb;
    TextView mText;

    // 底部控件
    private View footerView;
    private int footermeasuredHeight;
    /* 是否加载更多的标识 */
    private boolean isLoadMore;


    /**
     * 添加刷新头
     */
    private void addHeader() {
        headerView = View.inflate(getContext(), R.layout.header, null);

        mArrow = headerView.findViewById(R.id.arrow);
        mPb = headerView.findViewById(R.id.pb);
        mText = headerView.findViewById(R.id.text);
        // 隐藏刷新头
        // 获取刷新头的高度
        // 手动的测量
        // 测量的规则，如果0：表示不指定宽高，设置多少就是多少
        headerView.measure(0, 0);
        headermeasuredHeight = headerView.getMeasuredHeight(); // 获取测量的高度
        // 距离父控件的边缘的填充距离
        headerView.setPadding(0, -headermeasuredHeight, 0, 0);

        this.addHeaderView(headerView);//给listview添加头条目

        // 初始化旋转动画
        setAnimation();
    }

    private void addFooter() {
        footerView = View.inflate(getContext(), R.layout.footer, null);
        // 隐藏底部条目
        footerView.measure(0, 0);
        footermeasuredHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, 0, 0, -footermeasuredHeight);
        this.addFooterView(footerView);// 给listView的底部添加条目
    }

    /**
     * 初始化旋转动画
     */
    private void setAnimation() {
        // 箭头向上
        up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        up.setDuration(500);
        up.setFillAfter(true);// 保持动画结束的状态
        // 箭头向下
        down = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        down.setDuration(500);
        down.setFillAfter(true);// 保持动画结束的状态
    }

    // 下拉显示刷新头
    // 1. 触摸时间
    // 2. 下拉,通过移动的y坐标-按下的y坐标,如果值是大于0表示下拉
    // 3. 当前界面显示的第一个条目是listview的第一个条目
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                // 下拉,通过移动的y坐标-按下的y坐标，如果值是大于0表示下拉
                int distanceY = moveY - downY;
                // 处理判断是下拉之外，还要检测当前界面看到的第一个条目是否是listview的第一个条目
                // getFirstVisiblePosition : 获取当前界面显示的第一个条目的索引
                if (distanceY > 0 && getFirstVisiblePosition() == 0) {
                    // 计算空白的区域
                    int paddingTop = distanceY - headermeasuredHeight;
                    // 显示刷新头
                    headerView.setPadding(0, paddingTop, 0, 0);

                    // 如果有空白区域 下拉刷新 -> 松开刷新
                    if (paddingTop > 0 && CURRENTSTATE == PULL_DOWN) {
                        CURRENTSTATE = REFRESH;
                        switchOption();
                    }
                    // 如果没有空白区域 松开刷新 -> 下拉刷新
                    if (paddingTop < 0 && CURRENTSTATE == REFRESH) {
                        CURRENTSTATE = PULL_DOWN;
                        switchOption();
                    }
                    // 因为android系统的listview是没有显示空白区域的操作的，如果使用系统的触摸操作实现空白区域操作，会出现计算错误
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果是松开刷新 -> 正在刷新，并且刷新头显示出来
                if (CURRENTSTATE == REFRESH) {
                    CURRENTSTATE = REFRESHING;
                    headerView.setPadding(0, 0, 0, 0);
                    switchOption();
                    //将新的数据刷新出来
                    if (listenter != null) {
                        listenter.refresh();
                    }
                }
                // 如果是下拉刷新 -> 弹出隐藏
                if (CURRENTSTATE == PULL_DOWN) {
                    headerView.setPadding(0, -headermeasuredHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据状态改变控件的显示内容
     */
    private void switchOption() {
        switch (CURRENTSTATE) {
            case PULL_DOWN:
                mArrow.startAnimation(down);
                mText.setText("下拉刷新");
                break;
            case REFRESH:
                mArrow.startAnimation(up);
                mText.setText("松开刷新");
                break;
            case REFRESHING:
                mText.setText("正在刷新");
                mArrow.clearAnimation();//清除动画
                mArrow.setVisibility(View.GONE);
                mPb.setVisibility(View.VISIBLE);
                break;
        }
    }

    //创建回调，让activity可以监听下拉刷新操作，实现刷新数据操作
    public interface OnRefreshListener {
        /**
         * 下拉刷新
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadmore();
    }

    private OnRefreshListener listenter;
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listenter = listener;
    }

    /**
     * 取消刷新,结束刷新
     */
    public void finish() {
        // 正在刷新 -> 下拉刷新
        if (CURRENTSTATE == REFRESHING) {
            CURRENTSTATE = PULL_DOWN;
            mText.setText("下拉刷新");
            mPb.setVisibility(View.GONE);
            mArrow.setVisibility(View.VISIBLE);
            headerView.setPadding(0, -headermeasuredHeight, 0, 0);
        }
        if (isLoadMore) {
            // 取消加载更多
            footerView.setPadding(0, 0, 0, -footermeasuredHeight);
            isLoadMore = false;
        }
    }

    /**
     * 滚动状态改变时调用的方法
     * @param absListView
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 当滚动到listView的最后一个条目的时候,停止滚动,显示加载更多的条目
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  && getLastVisiblePosition() == this.getAdapter().getCount()-1 && isLoadMore == false) {
            isLoadMore = true;
            footerView.setPadding(0,0,0,0);
            // 当加载更多条目显示出来的时候,listView的条目数会自动+1
            // 需要重新定位最后一个条目
            this.setSelection(this.getAdapter().getCount()-1); // 定位选择哪个条目,条目索引
            if (listenter != null) {
                listenter.loadmore();
            }
        }
    }

    /**
     * 滚动调用
     * @param absListView
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

}

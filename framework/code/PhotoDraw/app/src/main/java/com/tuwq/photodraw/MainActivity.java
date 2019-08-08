package com.tuwq.photodraw;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.tuwq.photodraw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.pulltorefresh)
    PullToRefreshScrollView pulltorefresh;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //pulltorefresh = this.findViewById(R.id.pulltorefresh);
        // 设置下拉刷新和上拉加载模式
        pulltorefresh.setMode(PullToRefreshBase.Mode.BOTH);
        // 监听下拉刷新
        pulltorefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ScrollView> refreshView) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 访问网络
                        ScrollView scrollView = refreshView.getRefreshableView();
                        LinearLayout ll = (LinearLayout) scrollView.getChildAt(0);
                        Button button = new Button(getApplicationContext());
                        button.setText("我是下拉刷新出来的");
                        ll.addView(button,0);

                        // 恢复下拉刷新状态
                        pulltorefresh.onRefreshComplete();
                    }
                },3000);

            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ScrollView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 访问网络
                        ScrollView scrollView = refreshView.getRefreshableView();
                        LinearLayout ll = (LinearLayout) scrollView.getChildAt(0);
                        Button button = new Button(getApplicationContext());
                        button.setText("我是加载更多出来的");
                        ll.addView(button);

                        // 恢复下拉刷新状态
                        pulltorefresh.onRefreshComplete();
                    }
                },3000);
            }
        });
    }
}

package com.tuwq.pulltorefreshlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import com.tuwq.pulltorefreshlistview.view.PullToRefreshListView;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

    private PullToRefreshListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private int i = 0;
    private int j = 0;
    /**
     * 初始化控件
     */
    private void initView() {
        mListView = (PullToRefreshListView) findViewById(R.id.pulltorefreshlistview);

        final List<String> list = new ArrayList<String>();
        //显示数据
        for (int i = 0; i < 10; i++) {
            list.add("德玛西亚"+i+"区");
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
        mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0, "艾欧尼亚" + (++i) + "区");
                        adapter.notifyDataSetChanged();
                        // 刷新完成,取消刷新操作
                        mListView.finish();
                    }
                }, 2000);
            }
            @Override
            public void loadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(list.size(), "黑色玫瑰" + (++j) + "区");
                        adapter.notifyDataSetChanged();
                        // 刷新完成,取消刷新操作
                        mListView.finish();
                    }
                }, 2000);
            }
        });
    }
}

package com.tuwq.pulltorefreshlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import com.tuwq.pulltorefreshlistview.view.PullToRefreshListView;

import android.os.Bundle;
import android.app.Activity;
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

    /**
     * 初始化控件
     */
    private void initView() {
        mListView = (PullToRefreshListView) findViewById(R.id.pulltorefreshlistview);

        List<String> list = new ArrayList<String>();
        //显示数据
        for (int i = 0; i < 10; i++) {
            list.add("德玛西亚"+i+"区");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
    }
}

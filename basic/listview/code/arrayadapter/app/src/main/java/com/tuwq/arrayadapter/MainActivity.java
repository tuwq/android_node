package com.tuwq.arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_list = this.findViewById(R.id.lv_list);
        String[] objects = {"张三", "李四", "王五", "赵六", "孙七"};
        /**
         * arg1: 上下文
         * arg2；包含且只有一个textView的布局文件id
         * arg3: 要展示的数据
         * 每个条目中需要修改的数据十分简单,只有一个textView的内容需要修改,可以使用ArrayAdapter
         */
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.item, objects);
        stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.item2, R.id.tv_text, objects);
        lv_list.setAdapter(stringArrayAdapter);
    }
}

package com.tuwq.simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv_list = this.findViewById(R.id.lv_list);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> item1 = new HashMap<String, String>();
        item1.put("title", "高考成绩今日公布");
        item1.put("content", "今年全国高考结束之后,");
        data.add(item1);
        Map<String, String> item2 = new HashMap<String, String>();
        item2.put("title", "高空抛物可判死刑");
        item2.put("content", "随着各地高楼的涌现,高空抛物行为也是越来越多");
        data.add(item2);

        String[] from = {"title", "content"};
        int[] to = new int[]{R.id.tv_title, R.id.tv_content};
        /**
         * arg1: 上下文
         * arg2: 每一个条件的数据map的list集合
         * arg3: 要加载条目的布局id
         * arg4: string数组,item内容的key
         * arg5: int数组,item内容的view容器,需要和arg4位置下标对齐
         */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        lv_list.setAdapter(simpleAdapter);
    }
}

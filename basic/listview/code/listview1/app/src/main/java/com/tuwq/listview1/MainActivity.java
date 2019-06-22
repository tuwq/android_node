package com.tuwq.listview1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView lv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_list = this.findViewById(R.id.lv_list);
        lv_list.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        /**
         * 返回值决定当前listView展示多少条数据
         * @return
         */
        @Override
        public int getCount() {
            return 50000;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * 返回每个item的view
         * @param position index
         * @param convertView 可重用的view
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_text = null;
            System.out.println("getView被调用");
            if (convertView == null) {
                System.out.println("创建新的view对象");
                tv_text = new TextView(MainActivity.this);
            } else {
                System.out.println("重用view对象");
                tv_text = (TextView)convertView;
            }
            tv_text.setText("我是第" + position + "个条目");
            return tv_text;
        }
    }
}

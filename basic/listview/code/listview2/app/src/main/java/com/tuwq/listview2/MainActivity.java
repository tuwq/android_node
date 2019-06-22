package com.tuwq.listview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /**
             * arg1: 上下文
             * arg2: 资源item的id
             * arg3: viewGroup(LinearLayout,RelativeLayout)
             * 如果viewGroup传入一个具体的对象,那么这个方法创建出来的view对象就会作为这个viewGroup的childView
             * 现在只是想把xml文件转换成view对象,不需要放到viewGroup中,最后一个参数可以传null
             */
            View view = null;
            if (convertView == null) {
                // view = View.inflate(MainActivity.this, R.layout.item, null);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                // 以下两种方式也可以获取LayoutInflater
                // inflater = getLayoutInflater();
                // inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, null);
            } else {
                view = convertView;
            }
            return view;
        }
    }
}

package com.tuwq.swipedelete;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview = (ListView) findViewById(R.id.listview);

        listview.setAdapter(new MyAdapter());

        //给listveiw添加滚动监听器,滚动时关闭打开的侧拉
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //滑动的时候关闭已经打开的
                if(currentLayout!=null){
                    currentLayout.closeLayout();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        //条目点击已经无效,因为在onTouchEvent中已经消费了
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,"条目点击",0).show();
//            }
//        });
    }

    SwipeLayout currentLayout;//用来记录已经打开的布局
    class MyAdapter extends BaseAdapter implements SwipeLayout.OnSwipeListener{

        @Override
        public int getCount() {
            return Constant.NAMES.length;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.adapter_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(Constant.NAMES[position]);

            //给SwipeLayout设置滑动监听器
            holder.swipeLayout.setOnSwipeListener(this);

            //给swipeLayout设置点击事件
            holder.swipeLayout.setOnSwipeClickListener(new SwipeLayout.OnSwipeClickListener() {
                @Override
                public void onClick() {
                    Toast.makeText(MainActivity.this,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
        @Override
        public void onOpen(SwipeLayout swipeLayout) {
            //如果已经有打开的，那么则把打开的关掉
            if(currentLayout!=null && currentLayout!=swipeLayout){
                //关闭当前已经打开的那个
                currentLayout.closeLayout();
            }
            currentLayout = swipeLayout;
        }
        @Override
        public void onClose(SwipeLayout swipeLayout) {
            if(swipeLayout==currentLayout){
                currentLayout=null;
            }
        }
    }
    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.swipeLayout)
        SwipeLayout swipeLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


package com.tuwq.android5;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class RecycleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_recycle);

        //Toolbar的使用
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);//设置界面显示toolbar

        //SwipeRefreshLayout刷新操作
        final SwipeRefreshLayout spl = this.findViewById(R.id.spl);
        //刷新操作的监听
        spl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //实现刷新操作
            @Override
            public void onRefresh() {
                //设置SwipeRefreshLayout延迟操作
                spl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        spl.setRefreshing(false);//设置是否进行刷新操作，true刷新，false不刷新
                    }
                },3000);
            }
        });
        //设置刷新的箭头的颜色
        spl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView rlv = this.findViewById(R.id.rlv);
        //LinearLayoutManager
        //GridLayoutManager
        //StaggeredGridLayoutManager
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(staggeredGridLayoutManager);
        rlv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        /**
         * 设置条目的界面
         * @param viewGroup
         * @param i
         * @return
         */
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(RecycleActivity.this, R.layout.item, null);
            return new MyHolder(view);
        }

        /**
         * 设置条目的数据
         * @param viewHolder
         * @param i
         */
        @Override
        public void onBindViewHolder(@NonNull MyHolder viewHolder, int i) {
            viewHolder.setData(i);
        }

        /**
         * 设置条目的个数
         * @return
         */
        @Override
        public int getItemCount() {
            return 100;
        }

        /**
         * 用来初始化条目控件，设置条目控件显示的数据
         */
        class MyHolder extends RecyclerView.ViewHolder {
            View myItemView;
            TextView text1;
            TextView text2;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                myItemView = itemView;
                text1 = itemView.findViewById(R.id.text1);
                text2 = itemView.findViewById(R.id.text2);
            }
            public void setData(int position) {
                text1.setText("当前位置:" + position);
                text2.setText("显示内容:" + position);
                //设置条目的点击事件
                myItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RecycleActivity.this,text2.getText(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}

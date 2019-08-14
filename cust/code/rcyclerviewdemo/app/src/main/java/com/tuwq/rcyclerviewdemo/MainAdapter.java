package com.tuwq.rcyclerviewdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType){
            case 0:
                itemView = View.inflate(parent.getContext(),R.layout.item_image,null);
                break;
            case 1:
                itemView = View.inflate(parent.getContext(),R.layout.item_horizontal,null);
                break;
            default:
                itemView = View.inflate(parent.getContext(),R.layout.item_grid,null);
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int type = holder.getItemViewType();
        Context context = holder.itemView.getContext();
        switch (type){
            case 0:
                // 一张图片，不需要处理
                break;
            case 1:
                // 填充水平的列表
                LinearLayoutManager layout = new LinearLayoutManager(context);
                layout.setOrientation(RecyclerView.HORIZONTAL);
                holder.hRecycler.setLayoutManager(layout);
                holder.hRecycler.setAdapter(new HRecylerAdapter());
                break;
            default:
                // 填充九宫格
                holder.gridTitle.setText("当前商品类型为："+position);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                holder.gridRecycler.setLayoutManager(gridLayoutManager);
                holder.gridRecycler.setAdapter(new GridAdapter());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RecyclerView hRecycler;

        RecyclerView gridRecycler;
        TextView gridTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            // 水平的条目
            hRecycler = (RecyclerView) itemView.findViewById(R.id.h_recycler);

            // 九宫格的条目
            gridRecycler = (RecyclerView) itemView.findViewById(R.id.grid_recycler);
            gridTitle= (TextView) itemView.findViewById(R.id.grid_title);
        }
    }
}

package com.tuwq.rcyclerviewdemo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_grid_layout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.type.setText("这个商品的id为："+position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView type;
        public MyViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}

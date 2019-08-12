package com.tuwq.mobileplayer.adapter;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.Util;
import com.tuwq.mobileplayer.activity.PlayerActivity;
import com.tuwq.mobileplayer.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MvChildAdapter extends RecyclerView.Adapter<MvChildAdapter.MyViewHolder> {

    private List<VideoBean> videos;
    public MvChildAdapter(List<VideoBean> videos) {
        this.videos = videos;
    }

    @Override
    public MvChildAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_mvitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MvChildAdapter.MyViewHolder holder, int position) {
        // 获取当前条目的数据
        VideoBean videoBean = videos.get(position);

        // 填充文字
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getArtistName());
        holder.playCount.setText(videoBean.getDescription());

        // 填充图片
        Glide.with(holder.itemView.getContext()).load(videoBean.getPosterPic()).into(holder.ivPostimg);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.viewbgs)
        View viewbgs;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
        TextView playCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Point point = Util.computeImgSize(240,135,itemView.getContext());
            ivPostimg.getLayoutParams().width = point.x;
            ivPostimg.getLayoutParams().height = point.y;
            ivPostimg.requestLayout();

            viewbgs.getLayoutParams().width = point.x;
            viewbgs.getLayoutParams().height = point.y;
            viewbgs.requestLayout();

            // 注册当前条目的点击监听
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    VideoBean videoBean = videos.get(MyViewHolder.this.getAdapterPosition());
                    // 跳转到播放界面
                    Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                    intent.putExtra("url", videoBean.getUrl());
                    intent.putExtra("title", videoBean.getTitle());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

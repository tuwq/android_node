package com.tuwq.mobileplayer.adapter;

import android.content.Context;
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
import com.tuwq.mobileplayer.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<VideoBean> videoBeans;
    public HomeAdapter(List<VideoBean> videoBeen) {
        this.videoBeans = videoBeen;
    }

    /**
     * 创建薪的viewHolder,只在没有itemView时被调用
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.homepage_item, null);
        return new MyViewHolder(itemView);
    }

    /**
     * 为viewHolder绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
        // 获取到当前条目的数据
        VideoBean videoBean = videoBeans.get(position);

        // 填充内容
        holder.tvTitle.setText(videoBean.getTitle());
        holder.tvDescription.setText(videoBean.getDescription());

        // 加载图片
        Glide.with(holder.itemView.getContext()).load(videoBean.getPosterPic()).into(holder.ivContentimg);

        final int tag;
        String type = videoBean.getType();
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
            holder.ivType.setImageResource(R.drawable.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
            holder.ivType.setImageResource(R.drawable.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
            holder.ivType.setImageResource(R.drawable.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
            holder.ivType.setImageResource(R.drawable.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
            holder.ivType.setImageResource(R.drawable.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
            holder.ivType.setImageResource(R.drawable.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
            holder.ivType.setImageResource(R.drawable.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
            holder.ivType.setImageResource(R.drawable.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
            holder.ivType.setImageResource(R.drawable.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            tag = 9;
            holder.ivType.setImageResource(R.drawable.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(videoBean.getType())) {//打开页面
            tag = 10;
            holder.ivType.setImageResource(R.drawable.home_page_project);
        } else {
            tag = -100;
            holder.ivType.setImageResource(0);
        }
        // 更新当前条目的类型
        holder.tag = tag;
    }

    @Override
    public int getItemCount() {
        return videoBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item_logo)
        ImageView ivItemLogo;
        @Bind(R.id.iv_contentimg)
        ImageView ivContentimg;
        @Bind(R.id.iv_type)
        ImageView ivType;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.viewbg)
        View viewbg;

        int tag;// 当前条目的类型
        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // 初始化图片大小
            Point point = Util.computeImgSize(640,540,itemView.getContext());
            ivContentimg.getLayoutParams().width = point.x;
            ivContentimg.getLayoutParams().height = point.y;
            ivContentimg.requestLayout();

            viewbg.getLayoutParams().width = point.x;
            viewbg.getLayoutParams().height = point.y;
            viewbg.requestLayout();
            // 注册点击监听
            itemView.setOnClickListener(new OnClickListener());
        }
        private class OnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                // 获取当前被点击条目的数据
                VideoBean videoBean = videoBeans.get(MyViewHolder.this.getAdapterPosition());
                // 跳转到当前条目的响应界面
                Context context = itemView.getContext();
                Intent mIntent = new Intent();
                switch (tag){
                    case 0:
                    case 4:
                    case 10:
                        /*mIntent = new Intent(context, WebViewActivity.class);
                        mIntent.putExtra("url",videoBean.getUrl());
                        context.startActivity(mIntent);
                        break;*/
                    case 1:
                    case 5:
                    case 7:
                       /* mIntent = new Intent(context, PlayerActivity.class);
                        mIntent.putExtra("url",videoBean.getUrl());
                        mIntent.putExtra("title",videoBean.getTitle());
                        context.startActivity(mIntent);
                        break;*/
                    case 2:
                    case 3:
                        break;
                }
            }
        }
    }
}

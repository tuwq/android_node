package com.tuwq.mobileplayer.adapter;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.Util;
import com.tuwq.mobileplayer.bean.YueDanBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YueDanAdapter extends RecyclerView.Adapter<YueDanAdapter.MyViewHolder> {

    List<YueDanBean.PlayListsBean> playLists;

    public YueDanAdapter(List<YueDanBean.PlayListsBean> playLists) {
        this.playLists = playLists;
    }

    @Override
    public YueDanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.fragment_item_yuedan, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YueDanAdapter.MyViewHolder holder, int position) {
        // 获取当前条目的数据
        YueDanBean.PlayListsBean playListsBean = playLists.get(position);

        // 填充文本
        holder.title.setText(playListsBean.getTitle());
        holder.author.setText(playListsBean.getCreator().getNickName());
        holder.playCount.setText("收录高清Mv "+ playListsBean.getVideoCount()+" 首");

        // 填充图片
        Glide.with(holder.itemView.getContext()).load(playListsBean.getThumbnailPic()).into(holder.ivPostimg);
        Glide.with(holder.itemView.getContext()).load(playListsBean.getPlayListPic()).into(holder.civImg);
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.viewbgs)
        View viewbgs;
        @Bind(R.id.civ_img)
        de.hdodenhof.circleimageview.CircleImageView civImg;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
        TextView playCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // 初始化图片的大小
            Point point = Util.computeImgSize(240,135,itemView.getContext());
            ivPostimg.getLayoutParams().width = point.x;
            ivPostimg.getLayoutParams().height = point.y;
            ivPostimg.requestLayout();

            viewbgs.getLayoutParams().width = point.x;
            viewbgs.getLayoutParams().height = point.y;
            viewbgs.requestLayout();
        }
    }
}

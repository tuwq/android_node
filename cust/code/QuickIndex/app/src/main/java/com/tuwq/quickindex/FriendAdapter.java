package com.tuwq.quickindex;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendAdapter extends BaseAdapter {
    private ArrayList<Friend> list;

    public FriendAdapter(ArrayList<Friend> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.adapter_friend, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        Friend friend = list.get(position);

        //获取上一个条目的首字母
        String firstWord = friend.pinyin.charAt(0)+"";
        if(position>0){
            String lastLetter = list.get(position - 1).pinyin.charAt(0)+"";
            if(firstWord.equals(lastLetter)){
                //说明需要隐藏当前的首字母TextView
                holder.tvLetter.setVisibility(View.GONE);
            }else {
                //说明不想同，那么应该显示当前的首字母
                //需要显示的时候设置为可见
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(firstWord);
            }
        }else {
            //说明就是第0个条目,不需要跟上一个比较，直接显示
            //需要显示的时候设置为可见
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(firstWord);
        }

        holder.tvName.setText(friend.name);

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_letter)
        TextView tvLetter;
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

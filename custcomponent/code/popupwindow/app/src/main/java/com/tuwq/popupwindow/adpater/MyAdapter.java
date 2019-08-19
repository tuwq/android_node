package com.tuwq.popupwindow.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuwq.popupwindow.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<String>();
    private Context mContext;

    public MyAdapter(Context context) {
        this.mContext = context;
        for (int i = 0; i < 30; i++) {
            list.add("1000" + i);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mIcon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.mNumber = (TextView) view.findViewById(R.id.number);
            viewHolder.mDelete = (ImageView) view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //展示数据
        viewHolder.mNumber.setText(list.get(position));

        //点击删除按钮，删除条目
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    static class ViewHolder{
        ImageView mIcon,mDelete;
        TextView mNumber;
    }
}

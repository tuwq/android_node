package com.tuwq.googleplay95.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    ArrayList<T> list;

    public MyBaseAdapter(ArrayList<T> list) {
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
        //抽取原则：共同的操作留下，动态变化的可以用方法来获取
        Object holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), getItemLayoutId(position), null);
            holder = createViewHolder(convertView,position);//通过方法来获取，子类重写方法实现不一样的结果

            //设置tag
            convertView.setTag(holder);
        } else {
            holder =  convertView.getTag();
        }

        //绑定数据
        T t = list.get(position);

        bindViewHolder(t,holder,position);

        return convertView;
    }


    /**
     * 返回adapter的布局
     * @return
     */
    public abstract int getItemLayoutId(int position);

    /**
     * 子类实现该方法，返回自己的holder对象
     * @param convertView
     * @param position
     * @return
     */
    protected abstract Object createViewHolder(View convertView, int position);

    /**
     * 子类来实现绑定数据的
     * @param t
     * @param holder
     * @param position
     */
    protected abstract void bindViewHolder(T t, Object holder, int position);
}


package com.tuwq.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tuwq.zhbj.R;
import com.tuwq.zhbj.base.BaseFragment;

import java.util.List;

/**
 * 菜单页的fragment
 */
public class MenuFragment extends BaseFragment {

    private List<String> list;
    private ListView mListView;
    private Myadapter myadapter;

    @Override
    public View initView() {
        view = View.inflate(activity, R.layout.menufragment, null);
        return view;
    }

    @Override
    public void initData() {
        mListView = (ListView) view.findViewById(R.id.menufragment_lv_listview);
    }

    /**
     * 接受传递过来的数据
     *@param list
     */
    public void initList(List<String> list){
        this.list = list;

        //接受到传递过来的数据，通过listview展示数据
        if (myadapter == null) {
            myadapter = new  Myadapter();
            mListView.setAdapter(myadapter);
        }else{
            myadapter.notifyDataSetChanged();
        }
    }

    /**listview的adapter**/
    private class Myadapter extends BaseAdapter {

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
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            ViewHolder viewHolder;
            if (convertView == null) {
                v = View.inflate(activity, R.layout.menu_listview_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mArrow = (ImageView) v.findViewById(R.id.item_iv_arrow);
                viewHolder.mTitle = (TextView) v.findViewById(R.id.item_tv_title);
                v.setTag(viewHolder);
            }else{
                v = convertView;
                viewHolder = (ViewHolder) v.getTag();
            }
            viewHolder.mArrow.setImageResource(R.drawable.menu_arr_select);
            viewHolder.mTitle.setText(list.get(position));
            return v;
        }

    }

    static class ViewHolder{
        TextView mTitle;
        ImageView mArrow;
    }
}

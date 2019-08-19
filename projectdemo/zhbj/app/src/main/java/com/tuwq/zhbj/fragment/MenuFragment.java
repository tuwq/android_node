package com.tuwq.zhbj.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tuwq.zhbj.HomeActivity;
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

    /**保存被点击的条目的索引**/
    private int currentPostion;

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
        currentPostion = 0;//保证每次初始化显示数据的时候，都是从0开始，保证每次都是0索引条目为选中样式

        //接受到传递过来的数据，通过listview展示数据
        if (myadapter == null) {
            myadapter = new  Myadapter();
            mListView.setAdapter(myadapter);
        }else{
            myadapter.notifyDataSetChanged();
        }

        //设置listview的条目点击事件，实现点击条目切换条目样式的操作
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //切换点击条目的样式
                currentPostion = position;
                myadapter.notifyDataSetChanged();//会调用adapter的getcount和getView方法

                //切换完条目的样式，将侧拉菜单关闭
                slidingMenu.toggle();//如果侧拉菜单是关闭，执行打开，如果是打开的，执行关闭

                //切换新闻中心中显示的界面
                ((HomeActivity)activity).getHomeFragment().getNewsCenterpager().switchPage(position);
            }
        });
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
            viewHolder.mTitle.setText(list.get(position));

            if (currentPostion == position) {
                viewHolder.mArrow.setImageResource(R.drawable.menu_arr_select);
                viewHolder.mTitle.setTextColor(Color.RED);
            }else{
                viewHolder.mArrow.setImageResource(R.drawable.menu_arr_normal);
                viewHolder.mTitle.setTextColor(Color.WHITE);
            }
            viewHolder.mTitle.setText(list.get(position));

            if (currentPostion == position) {
                viewHolder.mArrow.setImageResource(R.drawable.menu_arr_select);
                viewHolder.mTitle.setTextColor(Color.RED);
            }else{
                viewHolder.mArrow.setImageResource(R.drawable.menu_arr_normal);
                viewHolder.mTitle.setTextColor(Color.WHITE);
            }
            return v;
        }

    }

    static class ViewHolder{
        TextView mTitle;
        ImageView mArrow;
    }
}

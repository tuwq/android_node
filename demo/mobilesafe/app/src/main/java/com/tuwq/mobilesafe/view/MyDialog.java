package com.tuwq.mobilesafe.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tuwq.mobilesafe.R;

/**
 * 自定义dialog
 */
public class MyDialog extends Dialog {

    private ListView mListView;

    public MyDialog(Context context) {
        super(context, R.style.AddressStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);//加载布局文件

        mListView = (ListView) findViewById(R.id.mydialog_lv_styles);
        mListView.setAdapter(new Myadapter());

        //1.样式问题;2.dialog显示在底部
        //2.显示在底部，在窗口的底部显示的
        //window : 窗口，是由windowManager负责管理
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        //相当于布局文件中给父控件设置的android:gravity=""属性
        // | :跟布局文件一样的效果，都是代表两种效果全部生效
        params.gravity=Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //将属性重新设置给窗口，使用更改的属性生效
        window.setAttributes(params);
    }

    private String[] titles = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "帽子绿" };
    private int[] icons = new int[] { R.drawable.toast_address_normal,
            R.drawable.toast_address_orange, R.drawable.toast_address_blue,
            R.drawable.toast_address_gray, R.drawable.toast_address_green };

    private class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getContext(), R.layout.mydialog_listview_item, null);
            ImageView mIcon = (ImageView) view.findViewById(R.id.item_iv_icon);
            TextView mTitle = (TextView) view.findViewById(R.id.item_tv_title);
            ImageView mIsSelected = (ImageView) view.findViewById(R.id.item_iv_isselected);

            //设置显示数据
            mIcon.setImageResource(icons[position]);
            mTitle.setText(titles[position]);

            return view;
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}

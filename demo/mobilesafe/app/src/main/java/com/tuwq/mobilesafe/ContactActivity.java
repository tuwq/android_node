package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuwq.mobilesafe.bean.ContactInfo;
import com.tuwq.mobilesafe.engine.ContactsEngine;

import java.util.List;

public class ContactActivity extends Activity {

    private ListView mContacts;
    private List<ContactInfo> contactsInfos;
    private ProgressBar mLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mContacts = (ListView) findViewById(R.id.contact_lv_contacts);
        mLoading = (ProgressBar) findViewById(R.id.contact_pb_loading);
        intData();

        mContacts.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //将选中的条目对应的号码传递给setUp3界面显示，同时还要移除界面
                Intent data = new Intent();
                data.putExtra("number", contactsInfos.get(position).number);
                //设置结果的方法，将结果返回给调用当前activity的activity
                /**
                 * arg1: 结果码
                 * arg2: 传递数据的意图
                 */
                setResult(Activity.RESULT_OK, data);
                //移除界面
                finish();
            }
        });
    }
    /**
     * 获取数据展示数据
     */
    private void intData() {
        //查询数据库是耗时操作，所以要放到子线程操作
        //在加载数据之前，显示进度条
        mLoading.setVisibility(View.VISIBLE);
        new Thread(){
            public void run() {
                contactsInfos = ContactsEngine.getContactsInfo(ContactActivity.this);
                //当数据查询完成，展示数据
                //运行在UI线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置listview的adapter
                        mContacts.setAdapter(new Myadapter());
                        //加载完数据，隐藏进度条
                        mLoading.setVisibility(View.GONE);
                    }
                });
            };
        }.start();
    }

    private class Myadapter extends BaseAdapter{
        @Override
        public int getCount() {
            return contactsInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return contactsInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                System.out.println("创建view对象，执行的findviewbyid操作");
                view = View.inflate(getApplicationContext(), R.layout.contact_listview_item, null);
                //1.创建盒子
                viewHolder = new ViewHolder();
                //2.将findviewbyid放到盒子中
                viewHolder.mIcon = (ImageView) view.findViewById(R.id.item_iv_icon);
                viewHolder.mName = (TextView) view.findViewById(R.id.item_tv_name);
                viewHolder.mNumber = (TextView) view.findViewById(R.id.item_tv_number);
                //3.将盒子和view对象进行绑定，一起复用
                view.setTag(viewHolder);//将viewHolder和view对象绑定
            }else{
                System.out.println("复用view对象，使用创建好的findviewbyid操作");
                view = convertView;
                //4.复用缓存，拿到缓存对象之后，就可以将和复用view对象绑定的veiwHolder获取出来
                viewHolder = (ViewHolder) view.getTag();//获取和view对象绑定的数据
            }
            //获取数据展示数据
            ContactInfo contactInfo = (ContactInfo) getItem(position);
            //5.使用盒子中保存的findviewbyid好的控件操作
            viewHolder.mName.setText(contactInfo.name);
            viewHolder.mNumber.setText(contactInfo.number);
            //显示头像，获取头像
            Bitmap bitmap = ContactsEngine.getContactPhoto(getApplicationContext(), contactInfo.id);
            if (bitmap != null) {
                viewHolder.mIcon.setImageBitmap(bitmap);
            }else{
                viewHolder.mIcon.setImageResource(R.drawable.ic_contact);
            }
            return view;
        }
    }
    //1.创建盒子
    static class ViewHolder{
        ImageView mIcon;
        TextView mName;
        TextView mNumber;
    }
}

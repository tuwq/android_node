package com.tuwq.sendsms;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tuwq.sendsms.bean.Contact;

import java.util.ArrayList;

public class ContactActivity extends Activity {

    protected static final int GET_CONTACT_RES = 2;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ListView lv_contact = (ListView) findViewById(R.id.lv_contact);

        for(int i = 0;i<20;i++){
            Contact contact = new Contact();
            contact.name="zhangsan"+i;
            contact.phone = "138888888"+i;
            contacts.add(contact);
        }

        MyAdapter adapter = new MyAdapter();
        lv_contact.setAdapter(adapter);

        lv_contact.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Contact contact = contacts.get(position);
                Intent data = new Intent();
                data.putExtra("phone", contact.phone);
                setResult(GET_CONTACT_RES, data);
                // 活动完成关闭页面后调用
                finish();
            }
        });
    }

    // 当返回按钮被按下就会走这个方法,onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView == null){
                view = View.inflate(getApplicationContext(), R.layout.item_contact, null);
            }else{
                view = convertView;
            }

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            Contact contact = contacts.get(position);

            tv_name.setText(contact.name);
            tv_phone.setText(contact.phone);
            return view;
        }
    }
}

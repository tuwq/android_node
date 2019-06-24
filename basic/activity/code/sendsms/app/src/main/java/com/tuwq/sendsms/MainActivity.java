package com.tuwq.sendsms;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GET_CONTACT_REQ = 1;
    private static final int GET_REPLY_REQ = 3;
    private EditText et_number;
    private EditText et_content;
    private Button btn_contact;
    private Button btn_reply;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = (EditText) findViewById(R.id.et_number);
        et_content = (EditText) findViewById(R.id.et_smsContent);

        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_reply = (Button) findViewById(R.id.btn_reply);
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_contact.setOnClickListener(this);
        btn_reply.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 联系页面
            case R.id.btn_contact:
                Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
                //startActivity(intent);
                // 跳转activity并可通知获取关闭时返回数据
                startActivityForResult(intent, GET_CONTACT_REQ);
                break;
            // 快速回复页面
            case R.id.btn_reply:
                Intent intent2 = new Intent(getApplicationContext(),ReplyActivity.class);
                startActivityForResult(intent2, GET_REPLY_REQ);
                break;
            // 发送消息
            case R.id.btn_send:
                SmsManager manager = SmsManager.getDefault();
                String destinationAddress = et_number.getText().toString().trim();
                String scAddress = null;
                String text = et_content.getText().toString().trim();
                PendingIntent sentIntent = null;
                PendingIntent deliveryIntent = null;
                manager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
                break;
        }
    }

    /**
     * 接收返回通知
     * @param requestCode startActivityForResult时的requestCode
     * @param resultCode  目标activity的resultCode
     * @param data 目标activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult");
        if(data==null){
            return;
        }
        switch (requestCode) {
            case GET_CONTACT_REQ:
                String phone = data.getStringExtra("phone");
                et_number.setText(phone);
                break;
            case GET_REPLY_REQ:
                String extra = data.getStringExtra("content");
                et_content.setText(extra);
                break;
        }
    }
}

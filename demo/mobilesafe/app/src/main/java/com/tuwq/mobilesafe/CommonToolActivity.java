package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tuwq.mobilesafe.bean.SMSInfo;
import com.tuwq.mobilesafe.engine.SmsEngine;
import com.tuwq.mobilesafe.view.SettingView;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CommonToolActivity extends Activity implements OnClickListener{

    private SettingView mAddress;
    private SettingView mCommonNumber;
    private SettingView mReadSMS;
    private SettingView mWirteSMS;
    private SettingView mApplock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tool);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mAddress = (SettingView) findViewById(R.id.commontool_sv_address);
        mCommonNumber = (SettingView) findViewById(R.id.commontool_sv_commonnumber);
        mReadSMS = (SettingView) findViewById(R.id.commontool_sv_readsms);
        mWirteSMS = (SettingView) findViewById(R.id.commontool_sv_writesms);
        mApplock = (SettingView) findViewById(R.id.commontool_sv_applock);

        //设置点击事件
        mAddress.setOnClickListener(this);
        //设置常用号码的条目的点击事件
        mCommonNumber.setOnClickListener(this);

        //设置短信备份和短信还原的条目的点击事件
        mReadSMS.setOnClickListener(this);
        mWirteSMS.setOnClickListener(this);

        //程序锁的点击事件
        mApplock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commontool_sv_address:
                //跳转到号码归属地查询界面
                Intent intent = new Intent(CommonToolActivity.this,AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.commontool_sv_commonnumber:
                //跳转到常用号码的操作
                Intent intent2 = new Intent(CommonToolActivity.this,CommonNumberActivity.class);
                startActivity(intent2);
                break;
            case R.id.commontool_sv_readsms:
                //备份短信
                //显示进度条对话框
                final ProgressDialog dialog = new ProgressDialog(CommonToolActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框进度的样式，显示圆形还是进度操作
                dialog.setCancelable(false);//设置对话框不可消失，true:消失,false:不可以消失
                dialog.show();
                new Thread(){
                    public void run() {
                        // 监听短信
                        SmsEngine.readSms(CommonToolActivity.this,new SmsEngine.ReadSmsListener() {
                            @Override
                            public void setProgress(int progress) {
                                dialog.setProgress(progress);
                            }
                            @Override
                            public void setMax(int max) {
                                dialog.setMax(max);
                            }
                        });
                        dialog.dismiss();
                    };
                }.start();
                break;
            case R.id.commontool_sv_writesms:
                //还原短信
                //读取文件中的短信
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("mnt/sdcard/sms.txt")));
                    String readLine = br.readLine();
                    //将json串转化成的list集合
                    Gson gson = new Gson();
                    //new TypeToken<List<SMSInfo>>(){}.getType()
                    List<SMSInfo> list = gson.fromJson(readLine, new TypeToken<List<SMSInfo>>(){}.getType());
                    for (SMSInfo smsInfo : list) {
                        ContentResolver contentResolver = getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put("address", smsInfo.address);
                        values.put("date", smsInfo.date);
                        values.put("type", smsInfo.type);
                        values.put("body", smsInfo.body);
                        Uri uri = Uri.parse("content://sms/");
                        contentResolver.insert(uri, values);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.commontool_sv_applock:
                Intent intent3 = new Intent(this,AppLockActivity.class);
                startActivity(intent3);
                break;
        }
    }
}

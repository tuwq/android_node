package com.tuwq.smsbackup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import com.tuwq.smsbackup.bean.Sms;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Xml;
import android.view.Menu;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

public class MainActivity extends Activity {
    private ArrayList<Sms> smsList = new ArrayList<Sms>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void querySms(View v){
        ContentResolver resolver = getContentResolver();
        // 通过查看smsProvider的代码发现 子路径传null,代表查询所有的短信
        Uri uri = Uri.parse("content://sms");
        String[] projection = {"address","date","body"};
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        while(cursor.moveToNext()){
            Sms sms = new Sms();
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String body = cursor.getString(2);
            sms.address = address;
            sms.body = body;
            sms.date = date;

            smsList.add(sms);
        }
        for(Sms sms:smsList){
            System.out.println(sms);
        }
    }

    public void backup(View v){
        // 将短信内容生成xml
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(openFileOutput("sms.xml", MODE_PRIVATE), "utf-8");
            // 设置编码 <?xml version="1.0" encoding="utf-8"?>
            serializer.startDocument("utf-8", true);
            //<SmsList>
            serializer.startTag(null, "SmsList");
            for(Sms sms:smsList){
                //<sms>
                serializer.startTag(null, "sms");
                //<address>address</address>
                serializer.startTag(null, "address");
                serializer.text(sms.address);
                serializer.endTag(null, "address");

                serializer.startTag(null, "body");
                serializer.text(sms.body);
                serializer.endTag(null, "body");

                serializer.startTag(null, "date");
                serializer.text(sms.date);
                serializer.endTag(null, "date");

                //</sms>
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "SmsList");
            serializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(View v){
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://sms");
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        values.put("address", "95555");
        values.put("body", "关于尽快xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        resolver.insert(url, values);
    }
}

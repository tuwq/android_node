package com.tuwq.xmlserialization;

import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SMS> smsList = new ArrayList<SMS>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 30; i++) {
            SMS sms = new SMS();
            sms.from = "10000" + i;
            sms.content = "content" + i;
            sms.time = "2019-6-21 21:41:" + i;
            smsList.add(sms);
        }
        for (SMS sms: smsList) {
            System.out.println(sms);
        }
    }

    public void saveSMS(View v) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<SMSList>");
        for(SMS sms:smsList){
            sb.append("<SMS>");

            sb.append("<from>");
            sb.append(sms.from);
            sb.append("</from>");

            sb.append("<content>");
            sb.append(sms.content);
            sb.append("</content>");

            sb.append("<time>");
            sb.append(sms.time);
            sb.append("</time>");

            sb.append("</SMS>");
        }
        sb.append("</SMSList>");

        String xml = sb.toString();
        try {
            FileOutputStream fos = openFileOutput("sms.xml", MODE_PRIVATE);
            fos.write(xml.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this, "构建完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSMS2(View v){
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(openFileOutput("smslist.xml", MODE_PRIVATE), "utf-8");
            serializer.startDocument("utf-8", true);
            // <SMSList>
            serializer.startTag(null, "SMSList");
            for(SMS sms:smsList){
                //<SMS>
                serializer.startTag(null, "SMS");

                serializer.startTag(null, "from");
                serializer.text(sms.from);
                serializer.endTag(null, "from");

                serializer.startTag(null, "content");
                serializer.text(sms.content);
                serializer.endTag(null, "content");

                serializer.startTag(null, "time");
                serializer.text(sms.time);
                serializer.endTag(null, "time");
                //</SMS>
                serializer.endTag(null, "SMS");
            }

            //</SMSList>
            serializer.endTag(null, "SMSList");
            serializer.endDocument();
            Toast.makeText(MainActivity.this, "构建完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseSMS(View v){
        ArrayList<SMS> SMSs = null;
        SMS sms = null;
        XmlPullParser pullParser = Xml.newPullParser();
        try {
            pullParser.setInput(openFileInput("sms.xml"), "utf-8");
            int eventType = pullParser.getEventType();
            while(eventType!= XmlPullParser.END_DOCUMENT){
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("SMSList".equals(pullParser.getName())){
                            SMSs = new ArrayList<SMS>();
                        }else if("SMS".equals(pullParser.getName())){
                            sms = new SMS();
                        }else if("from".equals(pullParser.getName())){
                            sms.from = pullParser.nextText();
                        }else if("content".equals(pullParser.getName())){
                            sms.content = pullParser.nextText();
                        }else if("time".equals(pullParser.getName())){
                            sms.time = pullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("SMS".equals(pullParser.getName())){
                            //°Ñ¶ÔÏóÌí¼Óµ½¼¯ºÏ
                            SMSs.add(sms);
                        }
                        break;
                }
                eventType = pullParser.next();
            }
            for(SMS sms1:SMSs){
                System.out.println(sms1);
            }
            Toast.makeText(MainActivity.this, "解析完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

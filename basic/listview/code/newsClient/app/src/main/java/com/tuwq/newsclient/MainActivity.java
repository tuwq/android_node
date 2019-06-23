package com.tuwq.newsclient;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv_list;
    String path = "http://192.168.147.3:9001/img/news.xml";
    ArrayList<NewsItem> newslist = new ArrayList<NewsItem>();

    final int GET_DATA = 0;
    private MyAdapter adapter;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_DATA:
                    System.out.println(lv_list);
                    System.out.println(adapter);
                    lv_list.setAdapter(adapter);
                    break;

            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_list = this.findViewById(R.id.lv_list);
        adapter = new MyAdapter();
        initData();
    }

    /**
     * 联网获取数据
     * xml解析转换成arraylist
     * 通过listView展示数据
     */
    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        XmlPullParser pullParser = Xml.newPullParser();
                        pullParser.setInput(inputStream, "utf-8");
                        int eventType = pullParser.getEventType();
                        NewsItem item = null;
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            switch (eventType) {
                                case XmlPullParser.START_TAG:
                                    if ("item".equals(pullParser.getName())) {
                                        item = new NewsItem();
                                    } else if ("title".equals(pullParser.getName())) {
                                        item.title = pullParser.nextText();
                                    } else if ("description".equals(pullParser
                                            .getName())) {
                                        item.description = pullParser.nextText();
                                    } else if ("image".equals(pullParser.getName())) {
                                        item.img = pullParser.nextText();
                                    } else if ("type".equals(pullParser.getName())) {
                                        item.type = pullParser.nextText();
                                    } else if ("comment".equals(pullParser
                                            .getName())) {
                                        item.comment  = pullParser.nextText();
                                    }
                                    break;
                                case XmlPullParser.END_TAG:
                                    if ("item".equals(pullParser.getName())) {
                                        newslist.add(item);
                                    }
                                    break;
                            }
                            eventType = pullParser.next();
                        }
                        for(NewsItem item1: newslist){
                            System.out.println(item1);
                        }
                        handler.sendEmptyMessage(GET_DATA);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newslist.size();
        }

        @Override
        public Object getItem(int position) {
            return newslist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView == null){
                view = View.inflate(MainActivity.this, R.layout.item, null);
            }else{
                view = convertView;
            }
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            SmartImageView iv_image = (SmartImageView) view.findViewById(R.id.iv_icon);

            NewsItem newsItem = newslist.get(position);
            tv_title.setText(newsItem.title);
            tv_content.setText(newsItem.description);
            if("1".equals(newsItem.type)){
                tv_comment.setText(newsItem.comment+"条评论");
                tv_comment.setTextColor(Color.BLACK);
            }else if("2".equals(newsItem.type)){
                tv_comment.setText("独家");
                tv_comment.setTextColor(Color.RED);
            }else if("3".equals(newsItem.type)){
                tv_comment.setText("专题");
                tv_comment.setTextColor(Color.BLUE);
            }
            iv_image.setImageUrl(newsItem.img);
            return view;
        }
    }
}

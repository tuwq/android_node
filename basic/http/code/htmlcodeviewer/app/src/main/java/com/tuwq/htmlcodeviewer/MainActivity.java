package com.tuwq.htmlcodeviewer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText et_url;
    View btn_show;
    TextView tv_code;
    // 创建一个handler消息队列处理消息,底层结构是链表linkedList
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            tv_code.setText(result);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = this.findViewById(R.id.et_url);
        btn_show = this.findViewById(R.id.btn_show);
        tv_code = this.findViewById(R.id.tv_code);

        btn_show.setOnClickListener(new MyOnClickListener());
    }


    private class MyOnClickListener implements View.OnClickListener {
        /**
         * 获取url
         * url联网
         * 联网后获取响应
         * 响应码正常获取响应内容
         * @param v
         */
        @Override
        public void onClick(View v) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    String urlPath = et_url.getText().toString().trim();
                    try {
                        URL url = new URL(urlPath);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(10000);
                        int code = connection.getResponseCode();
                        if (code == 200) {
                            InputStream inputStream = connection.getInputStream();
                            String result = StreamUtil.getStringFormInputStream(inputStream);
                            // 通过handler发送消息给主线程
                            Message message = new Message();
                            message.obj = result;
                            handler.sendMessage(message);
                            // tv_code.setText(result);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

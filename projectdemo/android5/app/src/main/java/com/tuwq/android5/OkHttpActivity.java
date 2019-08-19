package com.tuwq.android5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ok_http);

        this.findViewById(R.id.bt2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 通过okhttp请求网络
        new Thread(){
            @Override
            public void run() {
                request();
                super.run();
            }
        }.start();
    }

    /**
     * 请求网络
     */
    private void request() {
        //创建请求的客户端
        OkHttpClient okHttpClient = new OkHttpClient();
        //get请求
        //设置请求的链接操作
        Request request = new Request.Builder().url("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100").build();
        //创建请求操作
        Call call = okHttpClient.newCall(request);
        //post方法
        /*RequestBody requestbody = new FormEncodingBuilder().add("name","张三").add("psw","123").build();
        //设置请求的链接操作
        Request request = new Request.Builder().url("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100").post(requestbody).build();
        //创建请求操作
        Call call = okHttpClient.newCall(request);*/

        //发送json数据操作
       /* MediaType mediaType = MediaType.parse("application/json;charset=utf-8");//设置传输的类型的
        String json = "{name:\"张三\"}";//设置传输数据
        RequestBody requestbody = RequestBody.create(mediaType,json);
        //设置请求的链接操作
        Request request = new Request.Builder().url("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100").post(requestbody).build();
        //创建请求操作
        Call call = okHttpClient.newCall(request);*/
        try {
            //执行请求操作，获取服务器的返回数据
            Response response = call.execute();
            //判断是否请求成功
            if (response.isSuccessful()){
                String json = response.body().string();
                Log.e("OkHttpActivity", "OkHttpActivity,request: "+json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.tuwq.gzipdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        String picUrl = "http://192.168.78.83:8080/i.jpg";
        ImageLoader.getInstance().displayImage(picUrl, imageView);
    }


    public void onClick(View v) {
        // 1、创建请求
        Request request = new Request.Builder()
                .header("Accept-Encoding", "gzip")// Accept-Encoding:"gzip, deflate" 增加这个请求头告诉服务器要进行Gzip压缩
                .url("http://mobileif.maizuo.com/city").build();

        // 2、把请求放到队列中
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 请求失败", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Content-Encoding:"gzip" 查看响应头中有没有gzip压缩
                String value = response.header("Content-Encoding");
                String json;
                if ("gzip".equals(value)) {
                    // 有压缩数据，进行解压
                    Log.i(TAG, "onResponse: 数据有Gzip压缩");
                    json = unzip(response.body().byteStream());
                } else {
                    // 没有压缩数据，正常读取
                    Log.i(TAG, "onResponse: 数据没有Gzip压缩");
                    json = response.body().string();
                }
                Log.i(TAG, "onResponse: " + json);
            }
        };
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    /** 对Gzip进行解压 */
    private String unzip(InputStream inputStream) {
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String json = sb.toString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}


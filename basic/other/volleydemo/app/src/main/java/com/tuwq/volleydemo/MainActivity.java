package com.tuwq.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static android.R.attr.max;
import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
import static android.R.attr.scaleType;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public String jsonObjectUrl = "http://192.168.78.83:8080/jsonobject.json";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    // 请求失败的监听器
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "onErrorResponse: 请求失败", error);
        }
    };

    public void stringRequest(View v) {
        // 请求成功的监听器
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                byte[] bytes = new byte[0];
                try {
                    bytes = response.getBytes("iso-8859-1");
                    String json = new String(bytes, "UTF-8");
                    Log.i(TAG, "onResponse: " + json);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        // 1、创建请求，这种请求默认使用ISO-8859-1
        StringRequest request = new StringRequest(jsonObjectUrl, listener, errorListener);

        // 2、把请求放到队列中
        VolleyUtil.getInstance(this).getRequestQueue().add(request);

//        request.cancel(); //  取消当前的请求
//        VolleyUtil.getInstance(this).getRequestQueue().cancelAll(null); // 取消队列中所有的请求
//        request.setTag("abc");
//        VolleyUtil.getInstance(this).getRequestQueue().cancelAll("abc");    // 取消指定Tag的请求

        request.setTag(this);
    }

    public void jsonObjectRequest(View v) {

        JSONObject jsonRequest = null;  // 如果传空，则是get请求，否则是Post请求
        // 请求成功的监听器
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: " + response.toString());
            }
        };
        // 1、创建请求，这种请求默认使用UTF-8
        JsonObjectRequest request = new JsonObjectRequest(jsonObjectUrl, jsonRequest, listener, errorListener);
        request.setTag(this);

        // 2、把请求放到队列中
        VolleyUtil.getInstance(this).getRequestQueue().add(request);
    }

    public void jsonArrayRequest(View v) {
        String url = "http://192.168.78.83:8080/jsonarray.json";
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response.toString());
            }
        };
        // 1、创建请求，这种请求默认使用UTF-8
        JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener);
        request.setTag(this);

        // 2、把请求放到队列中
        VolleyUtil.getInstance(this).getRequestQueue().add(request);
    }

    @Override
    protected void onDestroy() {
        VolleyUtil.getInstance(this).getRequestQueue().cancelAll(this);
        super.onDestroy();
    }

    public void gsonRequest(View v) {
        Response.Listener<AppInfo> listener = new Response.Listener<AppInfo>() {
            @Override
            public void onResponse(AppInfo response) {
                Log.i(TAG, "onResponse: " + response.toString());
            }
        };
        // 1、创建请求，这种请求默认使用UTF-8
        GsonRequest<AppInfo> request = new GsonRequest<AppInfo>(jsonObjectUrl, AppInfo.class, listener, errorListener);
        request.setTag(this);

        // 2、把请求放到队列中
        VolleyUtil.getInstance(this).getRequestQueue().add(request);
    }

    public String picUrl = "http://192.168.78.83:8080/i.jpg";

    public void imageRequest(View v) {
        // 1、创建请求，这种请求默认使用UTF-8
        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        };
        int maxWidth = 200;   // 指定图片最大的宽
        int maxHeight = 50;  // 指定图片最大的高
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;    // 设置缩放类型
        Bitmap.Config config = Bitmap.Config.RGB_565; // 指定颜色存储配置
        ImageRequest request = new ImageRequest(picUrl, listener, maxWidth, maxHeight, scaleType, config, errorListener);

        // 2、把请求放到队列中
        VolleyUtil.getInstance(this).getRequestQueue().add(request);
    }


    public void imageLoader(View v) {
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                Log.i(TAG, "bitmap: " + bitmap);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: 请求失败", error);
            }
        };
        VolleyUtil.getInstance(this).getImageLoader().get(picUrl, listener);
        ViewPager viewPager = null;
        viewPager.setOffscreenPageLimit(0);
    }


    public void networkImageView(View v) {
        NetworkImageView niv = (NetworkImageView) findViewById(R.id.network_image_view);
        niv.setDefaultImageResId(R.mipmap.ic_launcher); // 设置默认图片
        niv.setErrorImageResId(R.mipmap.ic_launcher);   // 设置加载失败时显示的图片
        niv.setImageUrl(picUrl, VolleyUtil.getInstance(this).getImageLoader());
    }

}

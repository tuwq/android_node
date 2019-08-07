package com.tuwq.opennetlib.volley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tuwq.opennetlib.Api;
import com.tuwq.opennetlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolleyActivity extends Activity {
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
//        get();
        post();
    }

    private void post() {
        // 初始化Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        PostStringRequest postStringRequest = new PostStringRequest(Request.Method.POST, Api.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                text.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        postStringRequest.addParam("user","qwe");
        postStringRequest.addParam("pwd","123");
        queue.add(postStringRequest);
    }


    private void get(){
        // 初始化Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        // 创建请求
        StringRequest stringRequest = new StringRequest(Api.HELLO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                text.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // 把请求添加到队列中
        queue.add(stringRequest);
    }
}


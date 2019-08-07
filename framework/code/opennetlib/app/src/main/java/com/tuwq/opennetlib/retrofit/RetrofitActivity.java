package com.tuwq.opennetlib.retrofit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.tuwq.opennetlib.Api;
import com.tuwq.opennetlib.R;
import com.tuwq.opennetlib.bean.HelloBean;
import com.tuwq.opennetlib.bean.LoginBean;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends Activity {
    @BindView(R.id.text)
    TextView text;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)// Baseurl 必须以/结尾
                .addConverterFactory(GsonConverterFactory.create())// 添加json转换器
                .build();
    }

    @OnClick(R.id.button)
    public void onClick() {
        /*new Thread(){
            @Override
            public void run() {
                 get();
            }
        }.start();*/
//        get();
//        post();
//        upload();
        download();
    }

    private void download() {
        HeimaService heimaService = retrofit.create(HeimaService.class);
        Call<ResponseBody> download = heimaService.download("upload/a.jpg");
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte[] bytes = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    text.setBackgroundDrawable(new BitmapDrawable(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void upload() {
        HeimaService heimaService = retrofit.create(HeimaService.class);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/cyx.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file","cyx.jpg",requestBody);
        Call<ResponseBody> upload = heimaService.upload(body);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    text.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void post() {
        HeimaService heimaService = retrofit.create(HeimaService.class);
       /* Call<ResponseBody> login = heimaService.login("abc", "123");
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    text.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/

        Call<ResponseBody> login2 = heimaService.login2(new LoginBean("uio", "789"));
        login2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    text.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void get(){
        // 获取业务类对象
        /*HeimaService heimaService = retrofit.create(HeimaService.class);
        // 调用访问网络的方法，返回访问网络的请求
        Call<ResponseBody> hello = heimaService.hello("aa","890");
        // 执行访问网络,同步方法，需要自己开子线程
        try {
            Response<ResponseBody> result = hello.execute();
            // 获取服务器返回的结果
            String string = result.body().string();
            System.out.println(string);
//            text.setText(string);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*HeimaService heimaService = retrofit.create(HeimaService.class);
        Call<ResponseBody> hello = heimaService.hello("qwe", "soiu");
        // 异步请求
        hello.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    text.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/
        HeimaService heimaService = retrofit.create(HeimaService.class);
        Call<HelloBean> hello = heimaService.hello2("qwe", "soiu");
        // 异步请求
        hello.enqueue(new Callback<HelloBean>() {
            @Override
            public void onResponse(Call<HelloBean> call, Response<HelloBean> response) {
                try {
                    text.setText(response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HelloBean> call, Throwable t) {

            }
        });

    }
}

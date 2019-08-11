package com.tuwq.mobileplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.AreaBean;
import com.tuwq.mobileplayer.bean.VideoBean;
import com.tuwq.mobileplayer.fragment.homepage.HomeMvp;
import com.tuwq.mobileplayer.fragment.homepage.HomePresenter;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTestActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);

        testHomePresenter();
    }

    /**
     * 测试 presenter 是否可用
     */
    private void testHomePresenter() {
        HomePresenter presenter = new HomePresenter(new HomeMvp.View() {
            @Override
            public void setData(List<VideoBean> videoBeen) {
                if (videoBeen.size() !=10){
                    throw new RuntimeException("请求主页的数据，返回数据不足10个");
                }
            }
            @Override
            public void onError(int code, Exception e) {
                throw new RuntimeException("错误代码为："+code+"。请求主页的数据，发生异常，e="+e);
            }
        });
        presenter.loadData(0,10);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        String url = URLProviderUtil.getMVareaUrl();
        LogUtils.e(TAG,"OkHttpTestActivity.loadData,url="+url);

        HttpManager.getInstance().get(url, new BaseCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                LogUtils.e(TAG,"OkHttpTestActivity.onFailure,e="+e);
            }
            @Override
            public void onSuccess(List<AreaBean> areaBeen) {
                LogUtils.e(TAG,"OkHttpTestActivity.onSuccess,areaBeen="+areaBeen.size());
                Toast.makeText(OkHttpTestActivity.this, "哈哈哈哈", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 在子线程发起 post 请求
     * @param url
     */
    private void postInChildThread(String url) {
        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建表单
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("offset","0");
        bodyBuilder.add("size","10");
        // 创建请求体对象
        RequestBody body = bodyBuilder.build();
        // 创建请求参数
        Request request = new Request.Builder().url(url).post(body).build();
        // 创建请求对象
        Call call = okHttpClient.newCall(request);
        // 发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result = response.body().string();
                    LogUtils.e(TAG,"OkHttpTestActivity.postInChildThread,result="+result);
                }
            }
        });
    }

    /**
     * 在子线程发起网络请求
     * @param url
     */
    private void getInChildThread(String url) {
        // 创建请求客户端
        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建请求参数
        Request request = new Request.Builder().url(url).build();
        // 创建请求对象
        Call call = okHttpClient.newCall(request);
        // 发起异步的请求
        call.enqueue(new Callback() {
            @Override
            // 请求发生异常
            public void onFailure(Call call, IOException e) {

            }
            @Override
            // 获取到服务器数据。注意：即使是 404 等错误状态也是获取到服务器数据
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result = response.body().string();
                    LogUtils.e(TAG,"OkHttpTestActivity.getInChildThread,result="+result);
                }
            }
        });
    }

    /**
     * 子线程中获取网络信息
     * @param url
     */
    private void getMethod(final String url) {
        new Thread(){
            @Override
            public void run() {
                try {
                    // 创建请求客户端
                    OkHttpClient okHttpClient = new OkHttpClient();
                    // 创建请求参数
                    Request request = new Request.Builder().url(url).build();
                    // 创建请求对象
                    Call call = okHttpClient.newCall(request);
                    // 执行请求，获取服务器响应
                    Response response = call.execute();
                    // 获取服务器数据
                    if (response.isSuccessful()){
                        String result = response.body().string();
                        LogUtils.e(TAG,"OkHttpTestActivity.getMethod,result="+result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

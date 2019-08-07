package com.tuwq.opennetlib.ion;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.tuwq.opennetlib.Api;
import com.tuwq.opennetlib.R;
import com.tuwq.opennetlib.bean.HelloBean;
import com.tuwq.opennetlib.bean.LoginBean;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IonActivity extends Activity {
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
//        post();
//        upload();
        download();
    }

    private void download() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/b.jpg");
        Ion.with(this)
                .load(Api.DOWNLOAD)// 下载文件的地址
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        System.out.println(downloaded*100f/total +"%");
                    }
                })// 获取下载的进度
                .write(file)// 指定文件下载的路径
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        if(e==null){
                            text.setText(result.getAbsolutePath());
                        }
                    }
                });
    }

    private void upload() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/cyx.jpg");
        Ion.with(this)
                .load(AsyncHttpPost.METHOD, Api.UPLOAD)
                .uploadProgress(new ProgressCallback() {// 获取上传进度
                    @Override
                    public void onProgress(long uploaded, long total) {
                        System.out.println(uploaded*100f/total +"%");
                    }
                })
                .setMultipartFile("file",file)// 上传文件
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if(e==null){
                            text.setText(result);
                        }
                    }
                });
    }

    private void post() {
        Ion.with(this)
                .load(AsyncHttpPost.METHOD,Api.LOGIN)// post请求
//                .setBodyParameter("user","sfa")
//                .setBodyParameter("pwd","123")// 设置请求的参数
                .setJsonPojoBody(new LoginBean("aaa","123"))// 往服务器提交json格式的数据
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if(e==null){
                            text.setText(result);
                        }
                    }
                });

    }

    private void get(){
        // 使用Ion
        // 初始化Ion对象
        /*Ion.with(this)
                .load(Api.HELLO)// 指定访问网络地址  默认是get请求
                .asString()// 设置访问网络回来数据的类型
                .setCallback(new FutureCallback<String>() {// 处理回来的数据
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if(e==null){
                            text.setText(result);
                        }
                    }
                });*/
        // 初始化Ion对象
        Ion.with(this)
                .load(Api.HELLO)// 指定访问网络地址  默认是get请求
                .as(HelloBean.class)// 设置访问网络回来数据的类型
                .setCallback(new FutureCallback<HelloBean>() {// 处理回来的数据
                    @Override
                    public void onCompleted(Exception e, HelloBean result) {
                        if(e==null){
                            text.setText(result.toString());
                        }
                    }
                });
    }
}


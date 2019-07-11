package com.tuwq.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.tuwq.mobilesafe.utils.PackageUtil;
import com.tuwq.mobilesafe.utils.SystemConstants;
import com.tuwq.mobilesafe.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity {

    private static final String CONNECTIONURL = "http://192.168.147.3:9001/datas/update.json";
    protected static final String TAG = "SplashActivity";
    // 保存文件的路径
    private static final String SAVEURL = "mnt/sdcard/mobilesafe_2.apk";
    TextView mVersion;

    int mNewsCode;
    String mNewsUrl;
    String mNewsMsg;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题栏,必须放在setContentView之前执行,只在当前的activity生效
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_splash);
        this.initView();

        //拷贝数据库
        copyDB("address.db");
    }

    /**
     * 拷贝数据库的方法
     *@param string
     */
    private void copyDB(String dbName) {
        //判断如果数据库已经拷贝成功，不需要再次拷贝
        File file = new File(getFilesDir(), dbName);
        if (!file.exists()) {
            //打开assets中保存的资源
            //1.获取assets目录的管理者
            AssetManager assets = getAssets();
            InputStream in=null;
            FileOutputStream out = null;
            try {
                //2.读取数据资源
                in = assets.open(dbName);
                //getFilesDir() : data -> data -> 应用程序的包名 -> files
                //getCacheDir() : data -> data -> 应用程序的包名 -> cache
                out = new FileOutputStream(file);
                //3.读写操作
                byte[] b = new byte[1024];//缓冲区域
                int len = -1; //保存读取的长度
                while((len = in.read(b)) != -1){
                    out.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void initView() {
        mVersion = this.findViewById(R.id.splash_tv_version);
        // 获取当前应用程序的版本名称,设置给textView展示
        mVersion.setText("版本:" + PackageUtil.getVersionName(getApplicationContext()));
        // 延迟几秒钟 实现更新操作
        /**
         * r: handler接收消息执行的操作
         * delayMillis: 延迟时间
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //根据设置中心中保存的开关状态，设置是更新还是不更新的操作
                boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), SystemConstants.ISUPDATE, true);
                if (b) {
                    update();
                }else{
                    enterHome();
                }
            }
        }, 1000);
        /*new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                update();
            }
        }.sendEmptyMessageDelayed(0, 2000); // 延迟几秒后给handler发送消息*/
    }

    private void update() {
        // 链接服务器,获取服务器数据,判断是否有最新版本
        // 链接服务器 a.联网操作 耗时操作 子线程中 b.权限 c.httpUrlConnection
        // connTimeout: 链接超时时间
        HttpUtils httpUtils = new HttpUtils(2000);
        // 链接请求服务器
        /**
         * method: 请求方式
         * url: 请求路径
         * params: 请求参数
         * RequestCallBack: 回调函数
         */
        httpUtils.send(HttpMethod.GET, CONNECTIONURL, null, new RequestCallBack<String>() {
            /**
             * @param responseInfo 保存服务器返回的数据
             */
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // 链接成功,获取返回数据,问题: 服务会返回那些数据
                // code: 新版本的版本号 apkUrl: 新版本下载路径 msg: 更新版本的描述信息,更新内容的操作
                // 如果封装数据 xml json
                String json = responseInfo.result;
                Log.i(TAG, json);
                // 解析服务器返回的json
                processJson(json);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                // 请求失败跳转首页
                enterHome();
            }
        });
    }

    private void processJson(String json) {
        // 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);
            // name: key(键)
            mNewsCode = jsonObject.getInt("code");
            mNewsUrl = jsonObject.getString("apkurl");
            mNewsMsg = jsonObject.getString("msg");
            Log.i(TAG, "code:"+mNewsCode+" apkurl:"+mNewsUrl+"  msg:"+mNewsMsg);

            // 判断最新版本的apk版本号是否和当前应用程序的版本号一致 如果一致没有最新版本 如果不一致 有最新版本
            if (mNewsCode == PackageUtil.getVersionCode(this)) {
                // 如果一致,没有最新版本
                // 跳转首页
                enterHome();
            } else {
                // 如果不一致,有最新版本
                // 弹出最新版本对话框
                showUpdateDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新版本对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setCancelable(false);//设置对话框是否可以消失，true:可以，false:不可以,屏蔽返回键的操作
        // 设置标题
        builder.setTitle("最新版本："+mNewsCode+".0");
        //设置图标
        builder.setIcon(R.drawable.ic_launcher);
        //设置描述信息
        builder.setMessage(mNewsMsg);
        //监听对话框消失的操作
        builder.setOnCancelListener(new OnCancelListener() {
            //对话框消失调用的方法
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                //跳转到首页
                enterHome();
            }
        });
        //设置按钮
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //隐藏对话框
                dialog.dismiss();
                //3.下载最新版本的apk
                downloadAPK();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //隐藏对话框
                dialog.dismiss();
                //跳转到首页
                enterHome();
            }
        });
        //显示对话框
        builder.show();
        //builder.create().show();//效果一样
    }

    /**
     * 下载apk
     */
    private void downloadAPK() {
        // sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 显示下载的进度条的对话框
            showProgressDialog();
            // 链接服务器 下载最新版本
            HttpUtils httpUtils = new HttpUtils();
            /** 1.下载路径 2.写sd卡权限 3.判断sd卡是否挂载 4.生成一个2.0版本的apk
             * url: 下载路径
             * target: 保存路径
             * callback: 回调
             */
            httpUtils.download(mNewsUrl, SAVEURL, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT);
                    progressDialog.dismiss();
                    intallApk();
                }
                @Override
                public void onFailure(HttpException e, String s) {
                    // 隐藏对话框
                    progressDialog.dismiss();
                    enterHome();
                }
                /**
                 * @param total 下载总进度
                 * @param current 下载当前进度
                 * @param isUploading 是否支持回调上传
                 */
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    progressDialog.setMax((int)total);
                    progressDialog.setProgress((int)current);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "没有可用的sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 安装apk的操作
     */
    private void intallApk() {
        /**
         * <intent-filter>
             <action android:name="android.intent.action.VIEW" />
             <category android:name="android.intent.category.DEFAULT" />
             <data android:scheme="content" />  content://
             <data android:scheme="file" />  file:从文件中获取安装应用的程序
             <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        // 因为相互覆盖,所有直接使用setDataAndType
		/*intent.setData(Uri.fromFile(new File(SAVEURL)));
		intent.setType("application/vnd.android.package-archive");*/
        intent.setDataAndType(Uri.fromFile(new File(SAVEURL)), "application/vnd.android.package-archive");
        //startActivity(intent);
        //当跳转到的activity退出的时候，会调用当前activity的onActivityResult方法
        //requestCode :请求码
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 跳转到首页的操作
        enterHome();
    }

    /**
     * 显示下载进度条对话框
     */
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的样式
        progressDialog.setCancelable(false);// 设置对话框是否可以消失
        progressDialog.show();// 显示对话框
    }

    /**
     * 跳转首页
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        // 移动当前页面,不允许返回此页面
        finish();
    }
}

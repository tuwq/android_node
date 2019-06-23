package com.tuwq.netpicview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_url;
    Button btn_show;
    ImageView iv_pic;
    final int GET_DATA_SUCCESS = 1;
    final int NETWORK_ERROR = 2;
    final int SERVER_ERROR = 3;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    Bitmap bm = (Bitmap)msg.obj;
                    iv_pic.setImageBitmap(bm);
                    Toast.makeText(MainActivity.this, "从网络获取图片成功", Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(MainActivity.this, "联网错误", Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(MainActivity.this, "图片地址不正确无法显示", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = this.findViewById(R.id.et_url);
        btn_show = this.findViewById(R.id.btn_show);
        iv_pic = this.findViewById(R.id.iv_pic);
        et_url.setText("http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg");
        btn_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 检查是否存在缓存
        final File file = new File(getCacheDir(), "avatar.png");
        if (file != null && file.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            iv_pic.setImageBitmap(bitmap);
            Toast.makeText(this, "使用缓存图片", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                String path = et_url.getText().toString().trim();
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        // 缓存图片
                        FileOutputStream fos = new FileOutputStream(file);
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while((len = inputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Message msg = Message.obtain();
                        // 区分消息标记
                        msg.what = GET_DATA_SUCCESS;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.what = SERVER_ERROR;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = NETWORK_ERROR;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


}

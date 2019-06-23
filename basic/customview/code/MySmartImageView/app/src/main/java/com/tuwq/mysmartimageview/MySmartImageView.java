package com.tuwq.mysmartimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MySmartImageView extends ImageView {
    protected static final int GET_PIC_FAILED = 0;
    protected static final int GET_PIC_SUCCESS = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_PIC_SUCCESS:
                    Bitmap bm = (Bitmap) msg.obj;
                    setImageBitmap(bm);
                    break;
                case GET_PIC_FAILED:
                    setImageResource(msg.arg1);
                    break;
            }
        }
    };

    public MySmartImageView(Context context) {
        super(context);
    }

    /**
     * 两个参数的构造,多出AttributeSet属性集合,在解析xml文件,属性将会封装在AttributeSet中
     * 系统解析xml布局文件时,创建view对象就会调用这个两个参数的构造
     * @param context 上下文
     * @param attrs 属性集合
     */
    public MySmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 三个参数的构造,多出defStyleAttr样式,如果在xml文件中写了style属性,那么解析后就会转化为defStyleAttr
     * @param context 上下文
     * @param attrs 属性集合
     * @param defStyleAttr 样式
     */
    public MySmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageURL(final String path) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = Message.obtain();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void setImageURL(final String path, final int resId) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message msg  = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_PIC_SUCCESS;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.what = GET_PIC_FAILED;
                        msg.arg1 = resId;
                        handler.sendMessage(msg);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = GET_PIC_FAILED;
                    msg.arg1 = resId;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}

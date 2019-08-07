package com.tuwq.opennetlib;

import android.os.Handler;
import android.os.Message;

public class NetUtils {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                Object obj = msg.obj;
                callback.onSuccess(obj);
            }
        }
    };
    private CallBack callback;

    // 访问网络
    public void request(String url, final CallBack callBack){
        this.callback = callBack;
        new Thread(){
            @Override
            public void run() {
                // 访问网络，httpurlconnection httpclien socket
                Object result = new Object();
                // 把数据传递给调用者
//                callBack.onSuccess(result);
                handler.obtainMessage(1,result).sendToTarget();

                Message msg =  handler.obtainMessage(1,result);
                msg.sendToTarget();// handler.sendMessage(this);
            }
        }.start();
    }

    public interface CallBack{
        void onSuccess(Object obj);
        void onFailure();
    }
}

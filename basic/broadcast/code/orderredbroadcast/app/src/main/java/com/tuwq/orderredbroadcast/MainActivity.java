package com.tuwq.orderredbroadcast;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.view.View;

import com.tuwq.orderredbroadcast.receiver.FinalReceiver;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View v){
        Intent intent = new Intent();
        intent.setAction("com.tuwq.sendrice");
        String receiverPermission = null;
        BroadcastReceiver resultReceiver = new FinalReceiver();
        Handler scheduler = null;
        String initialData = "发放粮食 每人100斤";
        /**
         * arg1: 上下文
         * arg2: 收到广播时需要的权限
         * arg3: 最终广播的接收者,无论是否之间被中断都会接收
         * arg4: 最终广播接收者用到的handler,如果传null,会在主线程处理
         * arg5: 成功状态吗
         * arg6: 初始化数据
         */
        sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, Activity.RESULT_OK, initialData, null);
    }

}

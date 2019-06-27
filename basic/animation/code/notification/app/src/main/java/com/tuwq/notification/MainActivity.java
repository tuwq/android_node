package com.tuwq.notification;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

@SuppressLint("NewApi") public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View v){
        // 通过Notification.Builder 创建一个notification
        Notification.Builder builder = new Builder(this);
        // 通知第一次收到的时候,会在状态栏中显示文字 这个文字就是通过setTicker设置的
        builder.setTicker("账户异动通知: 您的账户涉嫌洗钱操作,已被检方冻结,解冻事宜请与王警官联系,1377778888");
        // 设置当前通知 用户点击后就消失掉
        builder.setAutoCancel(true);
        // 设置在通知栏中显示的大标题
        builder.setContentTitle("账户异动通知");
        // 设置在通知栏中显示的小的文字内容
        builder.setContentText("您的账户涉嫌洗钱操作,已被检方冻结,解冻事宜请与王警官联系,1377778888");
        // 设置在状态栏显示的小图标
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setOngoing(true);
        Intent intent2 = new Intent(this,MainActivity.class);
        /**
         * 延迟执行的意图 当通知被点击的时候,就会执行intent
         * 如果操作activity getActivity 如果操作service getService
         * arg1: 上下文
         * arg2: 第二个参数 请求码 用来区分不同的PendingIntent
         * arg3: 意图
         * arg4: 传FLAG_UPDATE_CURRENT 更新PendingIntent
         */
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent.getService();
        builder.setContentIntent(intent);
        Notification notification = builder.build();
        NotificationManager mananger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mananger.notify(1, notification);
        mananger.cancel(1);
    }

}

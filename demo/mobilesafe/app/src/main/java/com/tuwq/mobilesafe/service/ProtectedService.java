package com.tuwq.mobilesafe.service;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.tuwq.mobilesafe.R;
import com.tuwq.mobilesafe.SplashActivity;

public class ProtectedService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = new Notification();

        //设置通知栏显示的样式
        notification.contentView = new RemoteViews(getPackageName(), R.layout.protectedservice);

        //设置通知到来的显示的样式
        notification.icon = R.drawable.ic_default;
        notification.tickerText = "黑马提醒您...";

        //点击通知消息，打开应用程序
        Intent intent = new Intent(this, SplashActivity.class);
        notification.contentIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //id：前台服务的id
        //notification : 通知栏
        startForeground(100, notification);
    }

}

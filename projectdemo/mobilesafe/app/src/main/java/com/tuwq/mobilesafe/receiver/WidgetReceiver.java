package com.tuwq.mobilesafe.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.tuwq.mobilesafe.service.WidgetService;

public class WidgetReceiver extends AppWidgetProvider {

    //当接受到广播事件的调用
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
    //当xml文件中定义的更新时间到了，调用
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    //删除小控件的时候调用的方法
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    //当小控件可用的调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        //开启服务
        Intent intent = new Intent(context, WidgetService.class);
        context.startService(intent);
    }
    //是在onDeleted之后调用的，表示小控件不可用了
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //关闭服务
        Intent intent = new Intent(context,WidgetService.class);
        context.stopService(intent);
    }
}

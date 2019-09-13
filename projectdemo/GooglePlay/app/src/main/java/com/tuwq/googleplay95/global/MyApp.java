package com.tuwq.googleplay95.global;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApp extends Application {
    //全局的上下文,就是封装了公共模块api的对象，比如可以获取包名，可以获取Resource对象，SP对象，Window对象
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
}


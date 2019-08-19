package com.tuwq.mobilesafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
/**
 * 自定义application
 * Application ： 相当于应用程序，程序在运行的是，先执行的application，再执行的activity
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("application启动了.....");
        //currentThread : 获取当前的线程
        //setUncaughtExceptionHandler : 设置监听异常
        Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    private class MyUncaughtExceptionHandler implements UncaughtExceptionHandler{
        //当有未捕获的异常的时候调用的方法
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            System.out.println("发现了异常，捕获了异常");
            try {
                ex.printStackTrace(new PrintStream(new File("mnt/sdcard/error.log")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //自己杀死自己（闪退）
            android.os.Process.killProcess(android.os.Process.myPid());//myPid() : 获取当前进程的pid
        }
    }
}

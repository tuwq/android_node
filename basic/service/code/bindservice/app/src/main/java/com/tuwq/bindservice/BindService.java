package com.tuwq.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BindService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return new MyBinder();
    }


    public class MyBinder extends Binder {
        public void callShowToast(String s){
            showToast(s);
        }
        public void showToast2(String s){
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreate() {
        System.out.println("onCreate");
        super.onCreate();
    }

    public void showToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
    }


}

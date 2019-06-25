package com.tuwq.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private MyConnection conn;
    private BindService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        Intent service = new Intent(this,BindService.class);
        conn = new MyConnection();
        /**
         * arg1: intent
         * arg2: ServiceConnection 通过这个接口可以接收服务开启或停止的消息
         * arg3: 开启服务时操作的选项,一般传入BIND_AUTO_CREATE 自动创建service
         */
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void stop(View v) {
        unbindService(conn);
    }

    public void callServiceMethod(View v){
//		BindService service = new BindService();
//		service.showToast("hello");
        myBinder.callShowToast("hello");
        myBinder.showToast2("hello hello");
    }

    private class MyConnection implements ServiceConnection {
        /**
         * 绑定
         * 只有当service的onBind方法返回值不能null 才会调用onServiceConnected
         * @param name
         * @param service 远程过程调用
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("onServiceConnected");
            myBinder = (BindService.MyBinder) service;
        }

        /**
         * 当服务正常退出时不会调用
         * 服务崩溃时调用
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("onServiceDisconnected");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑service
        unbindService(conn);
    }
}

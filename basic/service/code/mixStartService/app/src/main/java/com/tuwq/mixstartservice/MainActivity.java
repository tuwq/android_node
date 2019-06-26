package com.tuwq.mixstartservice;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        Intent service = new Intent(this,MixStartService.class);
        startService(service);
    }

    public void stop(View v) {
        Intent service = new Intent(this,MixStartService.class);
        stopService(service);

    }

    public void bind(View v) {
        Intent service = new Intent(this,MixStartService.class);
        conn = new MyConnection();
        bindService(service, conn, BIND_AUTO_CREATE);

    }

    public void unbind(View v) {
        unbindService(conn);
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    }

}

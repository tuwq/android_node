package com.tuwq.startservice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        Intent service = new Intent(this,MyService.class);
        // 开启服务
        startService(service);
    }

    public void stop(View v) {
        Intent name = new Intent(this,MyService.class);
        // 停止服务
        stopService(name);
    }

}

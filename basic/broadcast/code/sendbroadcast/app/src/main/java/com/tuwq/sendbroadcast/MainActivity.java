package com.tuwq.sendbroadcast;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendbroadcast(View v){
        Intent intent = new Intent();
        intent.setAction("com.tuwq.broadcast");
        intent.putExtra("key", "hello");
        // 发送无序广播
        sendBroadcast(intent);
    }

}

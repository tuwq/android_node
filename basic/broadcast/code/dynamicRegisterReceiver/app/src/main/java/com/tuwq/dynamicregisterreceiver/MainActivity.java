package com.tuwq.dynamicregisterreceiver;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new ScreenLightReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(receiver, filter);

    }

    /**
     * 销毁activity时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 动态注册的广播接收者,在当前activity销毁时需要注销掉
        unregisterReceiver(receiver);
    }
}

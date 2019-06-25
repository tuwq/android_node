package com.tuwq.sendbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 接收无序广播
 */
public class CostumReceiver extends BroadcastReceiver {

    /**
     * 本应用和其他应用都可以接收到广播
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到无序广播:"+intent.getStringExtra("key"));
        // abortBroadcast();
    }

}

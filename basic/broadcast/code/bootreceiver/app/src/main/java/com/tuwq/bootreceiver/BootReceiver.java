package com.tuwq.bootreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("手机启动");
        Intent intent2 = new Intent(context,MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

}

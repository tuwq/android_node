package com.tuwq.phonerecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("开机启动");
        Toast.makeText(context, "开启启动录音", Toast.LENGTH_SHORT).show();
        Intent service = new Intent(context,RecordService.class);
        context.startService(service);
    }

}


package com.tuwq.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tuwq.mobilesafe.engine.ProcessEngine;

public class KillProcessReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //清理进程
        ProcessEngine.killALLProcess(context);
    }

}

package com.tuwq.dynamicregisterreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenLightReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if("android.intent.action.SCREEN_OFF".equals(action)){
            System.out.println("关屏");
        }else if("android.intent.action.SCREEN_ON".equals(action)){
            System.out.println("亮屏");
        }
    }

}

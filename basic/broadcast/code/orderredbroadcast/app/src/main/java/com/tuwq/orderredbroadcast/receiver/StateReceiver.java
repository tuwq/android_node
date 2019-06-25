package com.tuwq.orderredbroadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, resultData+"===="+1, Toast.LENGTH_SHORT).show();
        System.out.println("StateReceiver before:" + resultData);
        System.out.println("每人60斤 StateReceiver");
        setResultData("每人60斤 StateReceiver");
        // 中断广播
        abortBroadcast();
        System.out.println("全部扣下 StateReceiver");
    }

}

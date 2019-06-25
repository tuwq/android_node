package com.tuwq.orderredbroadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, resultData+"===="+3, Toast.LENGTH_SHORT).show();
        System.out.println("TownReceiver before:" + resultData);
        System.out.println("每人0斤 TownReceiver");
        setResultData("每人0斤 TownReceiver");

    }

}

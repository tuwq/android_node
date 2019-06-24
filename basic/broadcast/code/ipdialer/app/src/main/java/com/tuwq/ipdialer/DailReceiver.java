package com.tuwq.ipdialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class DailReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        String prefix = sp.getString("prefix", "17951");
        String number = getResultData();
        System.out.println("打电话"+number);
        setResultData(prefix + number);
    }
}

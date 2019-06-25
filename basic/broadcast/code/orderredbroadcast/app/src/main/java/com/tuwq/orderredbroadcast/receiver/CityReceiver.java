package com.tuwq.orderredbroadcast.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, resultData+"===="+2, Toast.LENGTH_SHORT).show();
        System.out.println("CityReceiver before:" + resultData);
        System.out.println("每人30斤 CityReceiver");
        setResultData("每人30斤 CityReceiver");
    }

}

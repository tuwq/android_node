package com.tuwq.orderredbroadcast.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FarmerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, resultData, Toast.LENGTH_SHORT).show();
        System.out.println("FarmerReceiver before:" + resultData);
        System.out.println("FarmerReceiver");
		// setResultData("FarmerReceiver");
    }

}

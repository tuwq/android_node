package com.tuwq.orderredbroadcast.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FinalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, "final:"+resultData, Toast.LENGTH_SHORT).show();
        System.out.println("FinalReceiver before:" + resultData);
        System.out.println("FinalReceiver 结束啦");
    }

}

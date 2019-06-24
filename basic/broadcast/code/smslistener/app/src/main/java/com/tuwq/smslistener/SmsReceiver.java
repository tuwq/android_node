package com.tuwq.smslistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到短信");
        // pdu protocal data unit
        Object[] object = (Object[]) intent.getExtras().get("pdus");
        for(Object obj:object){
            SmsMessage message = SmsMessage.createFromPdu((byte[])obj);
            String from = message.getOriginatingAddress();
            String messageBody = message.getMessageBody();
            System.out.println("from"+from+"body"+messageBody);
            if("12345".equals(from)){
                Intent data = new Intent();
                data.setAction("com.tuwq.getcode");
                data.putExtra("code", messageBody);
                context.sendBroadcast(data);
            }
        }
    }
}

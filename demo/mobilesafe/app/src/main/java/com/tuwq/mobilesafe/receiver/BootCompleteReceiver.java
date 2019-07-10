package com.tuwq.mobilesafe.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.tuwq.mobilesafe.utils.SharedPreferencesUtil;
import com.tuwq.mobilesafe.utils.SystemConstants;

/**
 * 监听手机重启的广播接受者
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("手机重启了...");

        //判断用户是否开启防盗保护，开启，执行判断操作，没有开启，不做任何操作
        boolean sp_protected = SharedPreferencesUtil.getBoolean(context, SystemConstants.PROTECTED, false);
        if (sp_protected) {
            //判断sim卡是否发生变化
            //1.获取保存的sim卡序列号
            String sp_sim = SharedPreferencesUtil.getString(context, SystemConstants.SIM, "");
            //2.重新获取当前手机的sim卡
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //tel.getLine1Number();//获取和SIM卡绑定的电话号码，在中国有时候获取不到
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("获取sim失败");
                return;
            }
            String sim = tel.getSimSerialNumber();//获取SIM卡的序列号，唯一标示
            //3.判断sim是否为null
            if (sp_sim != null && sim != null) {
                if (!sp_sim.equals(sim)) {
                    //4.发送报警短信
                    SmsManager smsManager = SmsManager.getDefault();
                    //destinationAddress : 收件人的号码
                    //scAddress : 服务中心的地址
                    //text : 发送的短信内容
                    //sentIntent : 判断是否发送成功
                    //deliveryIntent : 判断收件人是否接受成功
                    smsManager.sendTextMessage(SharedPreferencesUtil.getString(context, SystemConstants.SAFENUMBER, "5554"), null, "da ge wo bei dao le,Help Me", null, null);
                }
            }
        }
    }

}

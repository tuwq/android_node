package com.tuwq.googleplay95.util;

import android.widget.Toast;

import com.tuwq.googleplay95.global.MyApp;

public class ToastUtil {

    private static Toast toast;

    /**
     * 定义一个强大的，单例的吐司
     */
    public static void showToast(String text){
        if(toast==null){
            //创建吐司
            toast = Toast.makeText(MyApp.context,text,Toast.LENGTH_SHORT);
        }
        //直接更改吐司的文本
        toast.setText(text);
        //最后展示
        toast.show();

    }
}

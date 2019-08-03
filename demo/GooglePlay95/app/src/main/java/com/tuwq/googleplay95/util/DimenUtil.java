package com.tuwq.googleplay95.util;


import com.tuwq.googleplay95.global.MyApp;

public class DimenUtil {
    /**
     * 获取dimes.xml中定义的dp值，并会自动转为像素
     * @param resId
     * @return
     */
    public static int getDimens(int resId){
        return MyApp.context.getResources().getDimensionPixelSize(resId);
    }
}

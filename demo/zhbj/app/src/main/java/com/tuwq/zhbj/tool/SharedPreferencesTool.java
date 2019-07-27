package com.tuwq.zhbj.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的工具类
 */
public class SharedPreferencesTool {
    private static SharedPreferences sp;
    /**
     * 保存boolean信息的操作
     *@param context
     *@param key
     *@param value
     */
    public static void saveBoolean(Context context,String key,boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean信息的值
     *@param context
     *@param key
     *@param defvalue
     *@return
     */
    public static boolean getBoolean(Context context,String key,boolean defvalue){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defvalue);
    }
}

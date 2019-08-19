package com.tuwq.mobileplayer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

public class Util {

    private static final String TAG ="Util" ;

    /**
     * 遍历打印数据
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        Log.i(TAG, "printCursor: 条目个数"+cursor.getCount());
        while (cursor.moveToNext()) {
            Log.i(TAG, "==============================================");
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String value=cursor.getString(i);
                String name=cursor.getColumnName(i);
                Log.i(TAG, "printCursor: name="+name+";value="+value);
            }
        }
    }

    /**
     * 截取字符
     * @param name
     * @return
     */
    public static String formatName(String name) {
        if (!TextUtils.isEmpty(name)){
            int i = name.indexOf(".");
            String newName=name.substring(0,i);
            return newName;
        }
        return "";
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        int width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        int height = ((Activity)context).getWindow().getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }

    public static String getSystemversion(){
        return android.os.Build.VERSION.RELEASE;
    }
    public static String getPhoneModel(){
        return android.os.Build.MODEL;
    }

    /**
     * 根据毫秒数获取时间字符串
     * @param duration 毫秒数
     * @return
     */
    public static String formatDuration(long duration) {
        int HOUR = 60 * 60 * 1000;// 小时
        int MINUTE = 60 * 1000;//分钟
        int SECOND = 1000;//秒
        //计算小时
        int hour = (int) (duration / HOUR);
        long remain = duration % HOUR;
        //计算分钟
        int minute = (int) (remain / MINUTE);
        remain = remain % MINUTE;
        //计算秒
        int second = (int) (remain / SECOND);
        if (hour==0){
            return  String.format("%02d:%02d",minute,second);
        }else {
            return  String.format("%02d:%02d:%02d",hour,minute,second);
        }
    }

    /**
     * 计算图片控件的宽高,与宽度形成比例
     * @param picW 图片的高度
     * @param picH 图片的宽度
     * @param context 上下文
     * @return 计算出来的图片控件宽高，x 为宽度，y 为高度
     */
    public static Point computeImgSize(int picW, int picH, Context context) {
        int imgW = getScreenWidth(context);
        int imgH = picH * imgW / picW;
        return new Point(imgW, imgH);
    }

    /**
     * 格式化时间。00:01 或者 01:02:03
     * @param time
     * @return
     */
    public static String formatTime(int time){
//        time / 60 / 60 /1000
        // 使用 Calendar 获取小时数
        Calendar calendar = Calendar.getInstance();
        calendar.clear(); // 清空原有数据
        calendar.add(Calendar.MILLISECOND,time);
        int hour = calendar.get(Calendar.HOUR);
//        LogUtils.e(TAG,"Util.formatTime,hour="+hour);

        if (hour > 1){
            return (String) DateFormat.format("hh:mm:ss",calendar);
        }else{
            return (String) DateFormat.format("mm:ss",calendar);
        }
    }
}

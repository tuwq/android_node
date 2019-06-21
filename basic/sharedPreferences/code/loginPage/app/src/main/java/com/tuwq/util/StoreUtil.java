package com.tuwq.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StoreUtil {

    /**
     * 保存用户名密码至本地文件
     * @param username 用户名
     * @param password 密码
     * @return 是否保存成功
     */
    public static boolean saveInfoInfo(Context context, String username, String password) {
        String info = username + "##" + password;
        // File file = new File("data/data/com.tuwq/info.txt");
        // 使用上下文获取应用相关目录路径
        // File file = new File(context.getFilesDir().getAbsolutePath() + "/info.txt");
        try {
            // FileOutputStream fos = new FileOutputStream(file);
            FileOutputStream fos = context.openFileOutput("info.txt", Context.MODE_PRIVATE);
            fos.write(info.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取用户名密码
     */
    public static String[] readInfo(Context context) {
        // File file = new File("data/data/com.tuwq/info.txt");
        // File file = new File(context.getFilesDir().getAbsolutePath() + "/info.txt");
        try {
            // FileInputStream fis = new FileInputStream(file);
            FileInputStream fis = context.openFileInput("info.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String temp = reader.readLine();
            String[] split = temp.split("##");
            return split;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

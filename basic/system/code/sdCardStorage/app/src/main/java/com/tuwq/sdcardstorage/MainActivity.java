package com.tuwq.sdcardstorage;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_free = (TextView) findViewById(R.id.tv_freespace);
        TextView tv_total = (TextView) findViewById(R.id.tv_totalspace);

        File storageDirectory = Environment.getExternalStorageDirectory();
        long totalSpace = storageDirectory.getTotalSpace();
        long freeSpace = storageDirectory.getFreeSpace();

        // 设置大小
        String total = Formatter.formatFileSize(this, totalSpace);
        String free = Formatter.formatFileSize(this, freeSpace);

        tv_total.setText("总空间"+total);
        tv_free.setText("可用空间:"+free);

    }

    public void saveSdCard(View v) {
        // sdk卡状态可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/info.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("123".getBytes());
                fos.close();
                System.out.println("写入成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String temp = reader.readLine();
                System.out.println("读取内容:" + temp);
                fis.close();
                System.out.println("读取成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.tuwq.createpiccopy;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);
        // getResources()
        // 在res目录下加载进来bitmap 是不能修改
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat);
//        iv_image.setImageBitmap(bitmap);
//        bitmap.setPixel(30, 30, Color.RED);
//        iv_image.setImageBitmap(bitmap);
        /**
         * arg1: 图片宽度
         * arg2: 图片高度
         * arg3: 图片配置信息
         * 使用原图,创建一个可以修改bitmap对象
         */
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        // 画布,传递bitmap副本,通过画布向bitmap画内容,bitmap对象必须是mutable(可以修改的)
        Canvas canvas = new Canvas(copybm);
        // 矩阵
        Matrix matrix = new Matrix();
        // 画笔
        Paint paint = new Paint();
        /**
         * 传递原图
         */
        canvas.drawBitmap(bitmap, matrix, paint);
        for(int i = 0;i<30;i++){
            copybm.setPixel(30+i, 30+i, Color.RED);
        }
        iv_image.setImageBitmap(copybm);
    }



}

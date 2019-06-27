package com.tuwq.paintboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private Canvas canvas;
    private Paint paint;
    private ImageView iv_image;
    private Bitmap copybm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_image);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg);
        // 创建空的图片
        copybm = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(copybm);
        paint = new Paint();
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        iv_image.setImageBitmap(copybm);
        iv_image.setOnTouchListener(new OnTouchListener() {
            private float startX;
            private float startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    // 按下
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    // 移动
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();
                        canvas.drawLine(startX, startY, x, y, paint);
                        System.out.println("x= "+x+"y="+y);
                        iv_image.setImageBitmap(copybm);
                        startX = x;
                        startY = y;
                        break;
                    // 抬起
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                // 返回true说明当前控件消费了这一些类的touch事件
                // touch事件是由一个ACTION_DOWN ACTION_MOVE(0或多) 和 一个ACTION_UP组成的
                // 如果ACTION_DOWN的时候没有返回true则ACTION_MOVE ACTION_UP 就会交给父控件处理
                return true;
            }
        });
    }

    public void changeColor(View v){
        // 改色颜色
        paint.setColor(Color.RED);
    }

    public void bold(View v){
        // 改变粗细
        paint.setStrokeWidth(5);
    }

    public void save(View v){
        // 保存文件
        File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            /**
             * 把文件保存到sd卡上
             * arg1: 格式
             * arg2: 质量 0代表质量差体积小 1代表质量高体积大
             * arg3: 输出流 确定保存位置
             */
            copybm.compress(CompressFormat.PNG, 100, fos);
            // 让安卓重新读取sd卡内容
            Intent intent = new Intent();
            // ACTION_MEDIA_SCANNER_SCAN_FILE
            intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

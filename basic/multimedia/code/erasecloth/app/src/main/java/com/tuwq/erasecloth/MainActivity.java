package com.tuwq.erasecloth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Bitmap copybm;
    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_front);
        // 加载前面的图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.front);
        copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        // 将前面的图片复制一份进行修改
        Canvas canvas = new Canvas(copybm);
        canvas.drawBitmap(bitmap, new Matrix(), new Paint());
        iv_image.setImageBitmap(copybm);
        // 图片触摸监听器
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                         break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();
                        try {
                            for(int i=-20;i<=20;i++){
                                for(int j = -20;j<=20;j++){
                                    if(Math.sqrt(i*i+j*j)<=20){
                                        // 这里修改的是原图的x,y 对应的像素点的颜色
                                        // 但是获取的屏幕的坐标 如果图片是在ImageView上点对点的显示就不会有问题
                                        copybm.setPixel((int)x+i, (int)y+j, Color.TRANSPARENT);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iv_image.setImageBitmap(copybm);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}

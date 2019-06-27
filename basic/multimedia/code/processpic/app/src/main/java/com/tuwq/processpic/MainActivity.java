package com.tuwq.processpic;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv_image2 = (ImageView) findViewById(R.id.iv_image2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat);
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(copybm);
        Matrix matrix = new Matrix();
        // setRotates 设置旋转的角度
        // matrix.setRotate(90,copybm.getWidth()/2,copybm.getHeight()/2);
        //setTranslate 偏移位置 x偏移30
        // matrix.setTranslate(30, 0);
        //setScale 缩放 负数为镜像
        matrix.setScale(1f, -1f);
        //matrix.postTranslate(copybm.getWidth(), 0);
        matrix.postTranslate(0, copybm.getHeight());
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, matrix, paint);

        iv_image2.setImageBitmap(copybm);

    }
}

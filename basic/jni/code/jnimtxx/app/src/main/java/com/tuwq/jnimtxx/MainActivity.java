package com.tuwq.jnimtxx;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mt.mtxx.image.JNI;

public class MainActivity extends Activity {

    private ImageView iv_image;
    private JNI jni;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_image);

        String pathName = "mnt/sdcard/front.jpg";
        bitmap = BitmapFactory.decodeFile(pathName);
        iv_image.setImageBitmap(bitmap);
        jni = new JNI();

    }

    public void process(View v){
        // 获取图片的宽度高度
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width*height];
        /**
         * arg1: int类型的数组 用来保存图片的颜色信息 ARGB.8888 每一个像素用4byte来保存颜色信息
         * arg2: offset pixels数组中从哪个元素开始保存图片的颜色信息
         * arg3: 一般传图片的宽度,大于宽度将会导致重复平铺效果
         * arg4,arg5: 从bitmap中读取的第一个像素的坐标
         * arg6,arg7: 一共要读出多少行 多少列的像素信息
         * 这个方法运行之后 原图的颜色信息就保存到了pixels数组当中
         */
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        // 调用jni中的native方法 处理图片 这个方法运行之后 pixels内容就被改变了 变成了加了特效的颜色信息
        jni.StyleBaoColor(pixels, width, height);
        // 拿着处理好的像素数组创建一张新的图片 这张图片已经包含了特效
        Bitmap bitmap2 = Bitmap.createBitmap(pixels, width, height, bitmap.getConfig());
        // 把加了特效的图片显示到imageView上
        iv_image.setImageBitmap(bitmap2);
    }

}

package com.tuwq.loadpic;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ImageView iv_image;
    private String path = "mnt/sdcard/dog.jpg";
    private int screenWidth;
    private int screenHeight;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_pic);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        Point outSize = new Point();
        // 屏幕的大小
        getWindowManager().getDefaultDisplay().getSize(outSize);
        System.out.println("width==="+outSize.x+"height==="+outSize.y);
    }


    public void loadpic(View v){
        // 直接显示图片会导致内存溢出
//    	Bitmap bitmap = BitmapFactory.decodeFile(path);
//    	iv_image.setImageBitmap(bitmap);
        loadpic3();
    }

    public void loadpic1(){
        // options 通过创建option选项对象,告诉BitmapFactory在解码图片时
        // 采用一些配置好的设置信息 比如压缩图片的压缩设置 或者 是否加载完整图片
        BitmapFactory.Options options = new Options();
        // inSampleSize 通过配置这个值可以压缩图片
        // 如果inSampleSize=2 那么解码到内存中的宽度是原图的1/2,高度也是1/2
        // 解码器默认会按照2的幂指数来处理图片的压缩,所以可以传入2的幂指数来做作为inSampleSize的值,如 2 4 6 8
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        iv_image.setImageBitmap(bitmap);
    }

    public void loadpic2(){
        // 比较图片和屏幕的分辨率 如果图片分辨率比屏幕分辨率高
        // 可以使用图片宽度/屏幕宽度 图片的高度/ 屏幕的高度
        BitmapFactory.Options option = new Options();
        // inJustDecodeBounds 只解析图片的宽高以及类型
        // 如果这个参数设置为true,那么调用BitmapFactory.decodeXXXX方法 只会把图片的宽度高度类型读出来,不会解码图片
        option.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, option);
//    	if(bitmap==null){
//    		System.out.println("图片宽:"+option.outWidth+"¸高:"+option.outHeight);
//    	}
        int width = option.outWidth;
        int height = option.outHeight;
        // 只有当图片的宽度和高度大于屏幕的宽度和高度才压缩图片
        if(width>screenWidth||height>screenHeight){
            int widthIndex = Math.round((float)width/(float)screenWidth);
            int heightIndex = Math.round((float)height/(float)screenHeight);
            // 计算压缩比例,取宽高比例的最大值
            System.out.println("max:" + Math.max(widthIndex, heightIndex));
            option.inSampleSize = Math.max(widthIndex, heightIndex);
        }
        // 用计算好的inJustDecodeBounds加载图片
        // 先把inJustDecodeBounds设置为false,开始加载图片
        option.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, option);
        // 用view显示图片
        iv_image.setImageBitmap(bitmap);
    }

    public void loadpic3(){
        // options 通过创建option选项对象,告诉BitmapFactory在解码图片时
        // 采用一些配置好的设置信息 比如压缩图片的压缩设置 或者 是否加载完整图片
        BitmapFactory.Options option = new Options();
        option.inSampleSize = 2;
        Bitmap bitmap = null;
        int i = 1;
        // 不断尝试,尽快减少压缩比例
        for(;;){
            try {
                option.inSampleSize = i; // 1/{i} 1/1不压缩
                bitmap = BitmapFactory.decodeFile(path, option);
                break;
            } catch (Error e) {
                i*=2;
                System.out.println("i = "+i);
            }
        }
        // 用view显示图片
        iv_image.setImageBitmap(bitmap);
    }

}


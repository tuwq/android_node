package com.tuwq.gzipdemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        // 设置图片显示相关的设置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)       // 显示正在加载的过程中的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)     // 如果图片地址为空则显示这个图片
                .showImageOnFail(R.mipmap.ic_launcher)          // 如果图片加载失败则显示这个图片
                .cacheInMemory(true)    // 缓存到内存，默认不缓存
                .cacheOnDisk(true)      // 缓存到磁盘，默认不缓存
                .considerExifParams(true)   // 参考图片信息
//            .displayer(new RoundedBitmapDisplayer(20))  // 圆角效果，这个效果要求ImageView必须要有精确的宽高
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)   // 线程优先级
                .defaultDisplayImageOptions(options)        // 配置默认的图片显示选项
                .denyCacheImageMultipleSizesInMemory()      // 禁止在内存中缓存多种尺寸的图片
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 磁盘缓存文件名生成器
                .tasksProcessingOrder(QueueProcessingType.LIFO)         // 任务处理顺序
//                .writeDebugLogs() // Remove for release app             // 写ImageLoader的log信息，app上线时应该注释掉
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config); // 在使用ImageLoader的时候必须要先调用这个方法进行初始化
    }
}

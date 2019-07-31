package com.tuwq.zhbj.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

/**
 * 本地缓存图片
 */
public class LocalCacheBitmap {

    private static final String PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhbj95_cache";

    /**
     * 保存图片到本地
     *@param url
     *@param bitmap
     */
    public void savaLocalCacheBitmap(String url,Bitmap bitmap){
        System.out.println("缓存到本地中");
        File dir = new File(PATH);
        //判断文件夹是否存在
        //isDirectory : 判断是否是文件夹
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            //创建图片对应的文件
            File file = new File(dir, MD5Util.Md5(url).substring(0, 10));
            FileOutputStream stream = new FileOutputStream(file);
            //compress : 将bitmap以什么样的类型，什么样的质量，保存到哪个文件中
            //format : 图片的类型
            //quality : 图片的质量
            //stream : 输出流，表示写入到哪个文件中
            bitmap.compress(CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地图片的操作
     *@param url
     *@return
     */
    public Bitmap getLocalCacheBitmap(String url){
        System.out.println("从本地获取");
        //从本地文件中获取图片
        //因为如果保存图片失败，文件是没有的，所以也会报出错误
        try {
            File file = new File(PATH, MD5Util.Md5(url).substring(0, 10));
            //decodeFile : 将文件转化成bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}

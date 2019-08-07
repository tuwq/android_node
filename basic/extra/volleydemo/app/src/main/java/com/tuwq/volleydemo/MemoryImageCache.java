package com.tuwq.volleydemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class MemoryImageCache implements ImageLoader.ImageCache {

    private int maxSize = 4 * 1024 * 1024;  // 指定最大的缓存容量为4M

    private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(maxSize) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}


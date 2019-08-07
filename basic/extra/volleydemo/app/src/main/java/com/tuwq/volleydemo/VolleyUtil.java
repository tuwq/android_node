package com.tuwq.volleydemo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyUtil {

    public static VolleyUtil instance;

    private ImageLoader imageLoader;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader.ImageCache getImageCache() {
        return imageCache;
    }

    private RequestQueue requestQueue;
    private ImageLoader.ImageCache imageCache;

    private VolleyUtil(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageCache = new MemoryImageCache();
        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    public static VolleyUtil getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyUtil(context);
        }
        return instance;
    }
}

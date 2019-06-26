package com.tuwq.contentobserver;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.tuwq.provider");
        MyObserver observer = new MyObserver(new Handler());
        //
        /**
         * arg1: uri
         * arg2: 是否只需匹配前缀uri,如只需匹配com.tuwq.provider的com.tuwq
         * arg3: 观察者实现
         * 注册内容观察者
         */
        resolver.registerContentObserver(uri, false, observer);
    }


    private class MyObserver extends ContentObserver{

        public MyObserver(Handler handler) {
            super(handler);
        }

        /**
         * 当内容发送改变时调用
         * @param selfChange
         * @param uri
         */
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            System.out.println(uri);
        }
    }

}

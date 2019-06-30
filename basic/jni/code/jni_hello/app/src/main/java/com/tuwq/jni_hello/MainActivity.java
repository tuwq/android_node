package com.tuwq.jni_hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static{
        System.loadLibrary("hello");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jniHello(View v) {
        Toast.makeText(this, helloInc(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 通过native关键字声明了一个本地方法 本地方法不用实现,不需要用jni调用c的代码来实现
     * @return
     */
    public native String helloInc();

}

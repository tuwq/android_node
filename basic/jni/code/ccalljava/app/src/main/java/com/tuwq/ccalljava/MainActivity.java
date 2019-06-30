package com.tuwq.ccalljava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private JNI jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jni = new JNI();
    }

    public void callvoid(View v) {
        jni.callbackvoid();
    }

    public void callint(View v) {
        jni.callbackInt();
    }

    public void callstring(View v) {
        jni.callbackString();
    }

    public void calltoast(View v){
		// jni.callbackShowToast();
        callbackShowToast();
    }

    public native void callbackShowToast();

    public void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

package com.tuwq.activityifecycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 前台状态: 可见且可以跟用户进行交互
 * 暂停状态: 可见但不能被操作
 * 停止状态: 不可见也不能被操作
 * 销毁状态: actitivy被系统杀死或者调用finish方法主动退出
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Main onCreate");
    }
    public void open(View v){
        startActivity(new Intent(this,MainActivity.class));
    }

    public void open2(View v){
        startActivity(new Intent(this,SecondActivity.class));
    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Main onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Main onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Main onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Main onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Main onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Main onRestart");
    }

}

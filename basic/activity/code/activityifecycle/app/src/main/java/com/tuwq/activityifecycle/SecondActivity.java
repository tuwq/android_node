package com.tuwq.activityifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println("Second onCreate");
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
        System.out.println("Second onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Second onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Second onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Second onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Second onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Second onRestart");
    }

}

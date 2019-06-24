package com.tuwq.fistactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void openThird(View v){
        //Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
        Intent intent2 = new Intent();
        intent2.setClassName("com.tuwq.fistactivity", "com.tuwq.fistactivity.ThirdActivity");
        startActivity(intent2);
    }
}

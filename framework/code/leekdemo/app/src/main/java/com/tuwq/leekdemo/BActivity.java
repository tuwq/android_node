package com.tuwq.leekdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BActivity extends AppCompatActivity {

    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        app = (App) getApplication();
        app.addActivity(this);
    }

    public void click(View v){
        Intent intent = new Intent(this, AActivity.class);
        startActivity(intent);
    }
}

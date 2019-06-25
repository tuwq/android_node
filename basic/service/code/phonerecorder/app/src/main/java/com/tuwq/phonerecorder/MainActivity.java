package com.tuwq.phonerecorder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void start(View v) {
        // Intent service = new Intent(getApplicationContext(),RecordService.class);
        // startService(service);
        Toast.makeText(getApplicationContext(), "开启录音", Toast.LENGTH_SHORT).show();
    }

    public void stop(View v) {
        Toast.makeText(getApplicationContext(), "关闭录音", Toast.LENGTH_SHORT).show();
    }
}

package com.tuwq.fistactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载界面
        setContentView(R.layout.activity_main);
    }

     public void call(View v) {
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_CALL);
         intent.setData(Uri.parse("tel:" + 1306342));
         startActivity(intent);
     }

     public void openSecond(View v) {
         Intent intent = new Intent();
         intent.setAction("com.tuwq.second");
         intent.setData(Uri.parse("tuwq:" + 123));
         startActivity(intent);
     }

     public void openSecond2(View v) {
         Intent intent = new Intent();
         intent.setAction("com.tuwq.second2");
         // intent.setData(Uri.parse("tuwq:" + 123));
         // intent.setType("tuwenqiang/tuwq");
         intent.setDataAndType(Uri.parse("tuwq:" + 123), "tuwenqiang/tuwq");
         startActivity(intent);
     }
}

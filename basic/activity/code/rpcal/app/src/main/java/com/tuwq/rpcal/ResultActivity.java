package com.tuwq.rpcal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class ResultActivity extends Activity {

    private TextView tv_info;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_result = (TextView) findViewById(R.id.tv_result);


        Intent intent = getIntent();
        // 放置参数
        String name = intent.getStringExtra("name");
        boolean isMale = intent.getBooleanExtra("gender", true);
        tv_info.setText("姓名:"+name+"性别:"+(isMale ?"男":"女"));
        calcRP();
    }

    private void calcRP() {
        Random random = new Random();
        int result = random.nextInt(100);
        if(result>80){
            tv_result.setText("80+....");
        }else if(result>60){
            tv_result.setText("60-80");
        }else if(result>40){
            tv_result.setText("40-60");
        }else{
            tv_result.setText("40-");
        }
    }
}


package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LostFindActivity extends Activity {

    private TextView mAginEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mAginEnter = (TextView) findViewById(R.id.lostfind_tv_aginenter);
        //设置重新进入设置向导的点击事件
        mAginEnter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LostFindActivity.this,SetUp1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

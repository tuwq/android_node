package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SetUp1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up1);

    }

    @Override
    public boolean pre_activity() {
        Toast.makeText(getApplicationContext(), "已经是第一张了", 0).show();
        // 第一个界面的上一步是不能执行跳转操作
        return true;
    }

    @Override
    public boolean next_activity() {
        Intent intent = new Intent(this,SetUp2Activity.class);
        startActivity(intent);
        return false;
    }

}

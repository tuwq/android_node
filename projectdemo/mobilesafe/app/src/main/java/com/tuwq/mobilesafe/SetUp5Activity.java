package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tuwq.mobilesafe.utils.SharedPreferencesUtil;
import com.tuwq.mobilesafe.utils.SystemConstants;

public class SetUp5Activity extends BaseActivity {

    private CheckBox mProtected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up5);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mProtected = (CheckBox) findViewById(R.id.setup5_cb_protected);

        //2.当再次进入界面的时候，获取保存的状态，设置checkbox的选中状态
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), SystemConstants.PROTECTED, false);
        mProtected.setChecked(b);//设置checkbox是否选中

        //1.监听checkbox的选中操作，保存选中的状态
        mProtected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            //当checkbox执行选中操作的时候调用方法
            //buttonView : checkbox
            //isChecked : checkbox的当前的选中的状态
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //保存checkbox的选中状态的操作
                SharedPreferencesUtil.saveBoolean(getApplicationContext(), SystemConstants.PROTECTED, isChecked);
            }
        });
    }

    @Override
    public boolean pre_activity() {
        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean next_activity() {
        //判断是否开启防盗保护
        //判断checkbox是否选中
        //isChecked : 获取checkbox是否选中的操作
        if (!mProtected.isChecked()) {
            Toast.makeText(getApplicationContext(), "请开启防盗保护", Toast.LENGTH_SHORT).show();
            return true;
        }

        // 保存设置完成的标示
        SharedPreferencesUtil.saveBoolean(getApplicationContext(),
                SystemConstants.ISSJFDSETTING, true);
        Intent intent = new Intent(this, LostFindActivity.class);
        startActivity(intent);
        return false;
    }
}

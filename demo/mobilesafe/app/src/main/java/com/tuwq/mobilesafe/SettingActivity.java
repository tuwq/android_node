package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.tuwq.mobilesafe.utils.SystemConstants;
import com.tuwq.mobilesafe.utils.SharedPreferencesUtil;
import com.tuwq.mobilesafe.view.SettingView;

public class SettingActivity extends Activity {

    private SettingView mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mUpdate = (SettingView) findViewById(R.id.setting_sv_update);

        //设置自动更新的条目的点击事件
        update();
    }
    /**
     * 设置自动更新的条目的点击事件
     */
    private void update() {
        //再次进入界面的时候，获取保存的开关状态，根据保存的开关状态，设置界面开关操作
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), SystemConstants.ISUPDATE, true);
        mUpdate.setToggleOn(b);
        mUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启 ->点击关闭
                //关闭 -> 点击开启
                //问题：点击事件是在activity实现的，但是更改的图片是在自定义控件中
                //mUpdate.setToggleOn(true);
                //获取开关状态，根据开关状态来实现开启和关闭的切换操作
                mUpdate.toggle();
                //开启关闭成功，保存开关状态
                SharedPreferencesUtil.saveBoolean(getApplicationContext(), SystemConstants.ISUPDATE, mUpdate.istoggle());
            }
        });
    }
}

package com.tuwq.mobileplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.rl_clear_chche)
    RelativeLayout rlClearChche;
    @Bind(R.id.switch_push)
    SwitchCompat switchPush;
    @Bind(R.id.rl_switch_push)
    RelativeLayout rlSwitchPush;
    @Bind(R.id.switch_loadimg_no_wifi)
    SwitchCompat switchLoadimgNoWifi;
    @Bind(R.id.rl_loadimg_withwifi)
    RelativeLayout rlLoadimgWithwifi;
    @Bind(R.id.rl_about)
    RelativeLayout rlAbout;
    @Bind(R.id.activity_settings)
    LinearLayout activitySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        // 初始化标题栏
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("设置中心");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 获取缓存文件大小，并填充界面
        long fileSize = computeCacheSize();

        DecimalFormat format = new DecimalFormat("#.00M");
        String sizeStr = format.format(fileSize / 1024 / 1024f);
        cacheSize.setText(sizeStr);
    }

    /**
     * 计算 glide 缓存文件的大小
     * @return
     */
    private long computeCacheSize() {
        File cacheDir = Glide.getPhotoCacheDir(this);
        // 健壮性检查
        if (!cacheDir.exists()&&!cacheDir.isDirectory()) return 0;
        File[] childFiles = cacheDir.listFiles();
        long size = 0;
        for (File child : childFiles) {
            size += child.length();
        }
        return size;
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.rl_clear_chche, R.id.rl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_chche:
                cacheSize.setText("0.00M");
                new Thread(){
                    @Override
                    public void run() {
                        Glide.get(SettingsActivity.this).clearDiskCache();
                    }
                }.start();
                break;
            case R.id.rl_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

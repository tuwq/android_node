package com.tuwq.mobileplayer.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.bean.MusicBean;
import com.tuwq.mobileplayer.service.AudioService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioPlayerActivity extends AppCompatActivity {
    private static final String TAG = "AudioPlayerActivity";

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.tv_position)
    TextView tvPosition;
    @Bind(R.id.sk_position)
    SeekBar skPosition;
    @Bind(R.id.iv_playmode)
    ImageView ivPlaymode;
    @Bind(R.id.iv_pre)
    ImageView ivPre;
    @Bind(R.id.iv_pause)
    ImageView ivPause;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.iv_wave)
    ImageView ivWave;

    private ServiceConnection conn;
    private AudioService.AudioBinder audioBinder;
    private AudioReceiver audioReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        IntentFilter filter = new IntentFilter("com.tuwq.audio_prepared");
        audioReceiver = new AudioReceiver();
        registerReceiver(audioReceiver, filter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 开启播放服务
        // 复制一份intent
        // 把启动activity的intent复制成新的对象,并把启动目标替换掉
        Intent service = new Intent(getIntent());
        service.setClass(this, AudioService.class);

        conn = new ServiceConnection();
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    /**
     * 解绑操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑服务
        unbindService(conn);
        // 反注册广播
        unregisterReceiver(audioReceiver);
        // 停止播放歌曲
        audioBinder.stop();
    }

    /**
     * 点击操作
     * @param view
     */
    @OnClick({R.id.iv_back, R.id.iv_playmode, R.id.iv_pre, R.id.iv_pause, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_playmode:
                break;
            case R.id.iv_pre:
                break;
            case R.id.iv_pause:
                swicthPauseStatus();
                break;
            case R.id.iv_next:
                break;
        }
    }

    // 切换暂停/播放
    private void swicthPauseStatus() {
        audioBinder.switchPauseStatus();
        updatePauseBtn();
    }

    /**
     * 切换暂停与播放按钮样式
     */
    private void updatePauseBtn() {
        AnimationDrawable anim = (AnimationDrawable) ivWave.getDrawable();
        if (audioBinder.isPlaying()){
            ivPause.setImageResource(R.drawable.selector_btn_audio_play);
            // 开启示波器动画
            anim.start();
        }else{
            ivPause.setImageResource(R.drawable.selector_btn_audio_pause);
            anim.stop();
        }
    }

    private class ServiceConnection implements android.content.ServiceConnection {
        @Override
        // 服务绑定
        public void onServiceConnected(ComponentName name, IBinder service) {
            audioBinder = (AudioService.AudioBinder) service;
        }

        @Override
        // 服务解绑
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private class AudioReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.tuwq.audio_prepared".equals(action)){
                // 音乐开始播放
                updatePauseBtn();
                // 获取当前正在播放的歌曲
                MusicBean musicBean = (MusicBean) intent.getSerializableExtra("musicBean");

                //更新标题和歌手名
                tvTitle.setText(musicBean.title);
                tvArtist.setText(musicBean.artist);

            }
        }
    }
}

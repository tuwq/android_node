package com.tuwq.phonerecorder;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class RecordService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 电话管理器
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        // 电话监听器
        MyPhoneStateListener listener = new MyPhoneStateListener();
        // 开始绑定监听器
        manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private MediaRecorder recorder;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                // 空闲,录音结束
                case TelephonyManager.CALL_STATE_IDLE:
                    System.out.println("空闲,录音结束");
                    if(recorder != null){
                        try {
                            // 停止录音
                            recorder.stop();
                            // 重置recorder
                            recorder.reset();
                            // 释放对象
                            recorder.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                // 响铃,准备一个录音机
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃,准备一个录音机");
                    recorder = new MediaRecorder();
                    // 设置音频输入源,来源麦克风
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    // 设置音频输出格式,THREE_GPP这种格式体积小质量差
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    // 设置音频编码amr 早期彩铃手机上使用的音频格式一般用作手机铃声NB narrow band 窄带 WB wid band
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    // 设置输出后文件保存的路径
                    recorder.setOutputFile(getCacheDir()+"/"+incomingNumber+".3gp");
                    try {
                        // 录音机开始准备
                        recorder.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                // 接电话,开始录音
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("接电话,开始录音");
                    if (recorder == null) {
                        recorder = new MediaRecorder();
                        // 设置音频输入源,来源麦克风
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        // 设置音频输出格式,THREE_GPP这种格式体积小质量差
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        // 设置音频编码amr 早期彩铃手机上使用的音频格式一般用作手机铃声NB narrow band 窄带 WB wid band
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        // 设置输出后文件保存的路径
                        recorder.setOutputFile(getCacheDir()+"/"+incomingNumber+".3gp");
                        try {
                            recorder.prepare();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    recorder.start();   // Recording is now started
                    break;
            }
        }
    }
}

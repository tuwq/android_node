package com.tuwq.muiscplayererframework;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder implements IService{
        //
        public void callNext(){
            next();
        }
        //
        public void callPre() {
            pre();
        }
        //
        public void callplay() {
            play();
        }
        //
        public void callpause() {
            pause();
        }

        public void playHiFiMusic(){

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // MediaPlayer
        // 准备音乐播放器
        System.out.println("准备音乐播放器");
    }

    public void next() {
            System.out.println("下一首");
    }

    public void pre() {
        System.out.println("上一首");
    }

    public void play() {
        System.out.println("开始播放");
    }

    public void pause() {
        System.out.println("暂停");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("销毁service");
    }

}

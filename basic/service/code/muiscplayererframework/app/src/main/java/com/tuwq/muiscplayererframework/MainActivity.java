package com.tuwq.muiscplayererframework;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private MyConnection conn;
    private MusicPlayerService.MyBinder musicControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bindService
        Intent service = new Intent(this, MusicPlayerService.class);
        conn = new MyConnection();
        bindService(service, conn, BIND_AUTO_CREATE);
        // startService
        startService(service);
    }

    public void pre(View v) {
        musicControl.callPre();
    }

    public void play(View v) {
        musicControl.callplay();
    }

    public void pause(View v) {
        musicControl.callpause();
    }

    public void next(View v) {
        musicControl.callNext();
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicPlayerService.MyBinder) service;
            musicControl.playHiFiMusic();
            IService iservice = (IService) service;
            iservice.callpause();
            iservice.callpause();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

}

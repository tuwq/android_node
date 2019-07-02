package com.tuwq.pressurediagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
    private MyPressureView pressureView;
    static {
        System.loadLibrary("pressure");
    }

    // private ProgressBar pb_pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pressureView = (MyPressureView) findViewById(R.id.pressure);
    }

    public void start(View v) {
        new Thread() {
            public void run() {
                startMonitor();
            };
        }.start();
    }

    public void stop(View v) {
        stopMonitor();
    }

    // 将被c调用
    public void setPressure(int pressure) {
        // pb_pressure.setProgress(pressure);
        pressureView.setPressure(pressure);
    }

    public native void startMonitor();
    public native void stopMonitor();
}

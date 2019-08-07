package com.tuwq.opennetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetUtils netUtils = new NetUtils();
        netUtils.request("www.df.com", new NetUtils.CallBack() {
            @Override
            public void onSuccess(Object obj) {

            }
            @Override
            public void onFailure() {

            }
        });
    }
}

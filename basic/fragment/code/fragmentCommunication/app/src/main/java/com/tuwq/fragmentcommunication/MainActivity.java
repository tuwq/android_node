package com.tuwq.fragmentcommunication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.ll_left, new LeftFragment(),"left");
        transaction.replace(R.id.ll_right, new RightFragment(),"right");
        transaction.commit();
    }

}

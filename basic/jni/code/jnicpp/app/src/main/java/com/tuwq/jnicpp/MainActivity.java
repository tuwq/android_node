package com.tuwq.jnicpp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    static{
        System.loadLibrary("cpp");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton(View v){
        Toast.makeText(this, hellocpp(), Toast.LENGTH_SHORT).show();
    }

    public native String hellocpp();
}

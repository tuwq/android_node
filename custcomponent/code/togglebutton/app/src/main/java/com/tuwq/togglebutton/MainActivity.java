package com.tuwq.togglebutton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tuwq.togglebutton.view.MyToggleButton;

public class MainActivity extends Activity {

    private MyToggleButton mMyToggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView() {
        mMyToggleButton = (MyToggleButton) findViewById(R.id.mytogglebutton);
        mMyToggleButton.setOnToggleOnListener(new MyToggleButton.OnToggleOnListener() {
            @Override
            public void toggleOn(boolean isToggle) {
                Toast.makeText(getApplicationContext(), isToggle ? "开启" : "关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

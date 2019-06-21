package com.tuwq;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 按钮点击的两种方式
 *  new 匿名内部类
 *  实现OnClickListener接口
 *  layout中指定方法
 */
public class MainActivity extends Activity {

    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = this.findViewById(R.id.button1);
        button2 = this.findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button1");
            }
        });
        button2.setOnClickListener(new MyOnClickListener());
    }

    public void button3call(View v) {
        System.out.println("button3");
    }

    private class MyOnClickListener implements View.OnClickListener {
        // 当控件被点击的时候就会调用这个onClick方法
        @Override
        public void onClick(View v) {
            // 被点击控件的id
            v.getId();
            System.out.println("button2");
        }
    }

}

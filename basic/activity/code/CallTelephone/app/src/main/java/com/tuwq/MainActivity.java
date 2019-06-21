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
 * Activity 代表一个用户界面,每个android的界面对应一个Activity
 * Activity 可以创建一个窗口,在这个窗口上加载用户交互界面
 */
public class MainActivity extends Activity {

    EditText editText1;
    Button button1;

    /**
     * 当activity创建的时候就会调用onCreate,在onCreate中做初始化操作
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 调用setContentView方法加载界面加载到内存展示出来
        this.setContentView(R.layout.activity_main);
        button1 = this.findViewById(R.id.button1);
        editText1 = this.findViewById(R.id.editText1);
        button1.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        // 当控件被点击的时候就会调用这个onClick方法
        @Override
        public void onClick(View v) {
            String phoneNumber = editText1.getText().toString();
            if (TextUtils.isEmpty(phoneNumber)) {
                System.out.println("输出为空");
                /**
                 * arg1: 上下文,Activity继承自上下文
                 * arg2: 消息内容
                 * arg3: 显示时间长短
                 * show() 显示内容
                 */
                Toast.makeText(MainActivity.this, "电话号码不能为空", Toast.LENGTH_LONG).show();
            } else {
                System.out.println("打电话:" + phoneNumber);
                // 开启打电话的Activity
                Intent intent = new Intent();
                // 系统电话Activity
                intent.setAction(Intent.ACTION_CALL);
                // uri统一资源标识符,自定义协议,与url是父子类关系
                Uri data = Uri.parse("tel:" + phoneNumber);
                intent.setData(data);
                startActivity(intent);
            }
        }
    }

}

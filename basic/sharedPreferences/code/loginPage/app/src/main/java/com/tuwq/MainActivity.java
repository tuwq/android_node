package com.tuwq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tuwq.util.StoreUtil;

public class MainActivity extends AppCompatActivity {

    EditText et_username;
    EditText et_password;
    CheckBox cb_isSave;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = this.findViewById(R.id.et_username);
        et_password = this.findViewById(R.id.et_password);
        cb_isSave = this.findViewById(R.id.cb_isSave);
        btn_login = this.findViewById(R.id.btn_login);

        // 设置点击事件
        btn_login.setOnClickListener(new MyOnClickListener());
        // 获取用户保存的信息
        String[] split = StoreUtil.readInfo(this);
        if (split != null) {
            et_username.setText(split[0]);
            et_password.setText(split[1]);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        /**
         * 内容是否为空
         * 是否保存密码
         * @param v
         */
        @Override
        public void onClick(View v) {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                if (cb_isSave.isChecked()) {
                    if (StoreUtil.saveInfoInfo(MainActivity.this, username, password)) {
                        Toast.makeText(MainActivity.this, "用户名密码保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "用户名密码保存失败", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("MainActivity", "开始登录...");
            }
        }
    }
}

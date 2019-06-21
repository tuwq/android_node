package com.tuwq.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_username;
    EditText et_password;
    Button btn_login;
    CheckBox cb_isSave;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = this.findViewById(R.id.et_username);
        et_password = this.findViewById(R.id.et_password);
        btn_login = this.findViewById(R.id.btn_login);
        cb_isSave = this.findViewById(R.id.cb_isSave);
        btn_login.setOnClickListener(this);
        /**
         * arg1: 保存文件的名字
         * arg2: 存储的模式
         */
        sp = this.getSharedPreferences("info", MODE_PRIVATE);
        boolean isChecked = sp.getBoolean("isChecked", false);
        if (isChecked) {
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            et_username.setText(username);
            et_password.setText(password);
        }
        cb_isSave.setChecked(isChecked);
    }

    @Override
    public void onClick(View v) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            boolean checked = cb_isSave.isChecked();
            if (checked) {
                // 通过sp对象获取编辑器
                editor.putString("username", username);
                editor.putString("password", password);
            }
            editor.putBoolean("isChecked", checked);
            // 调用commit后,所有的put方法才会保存到文件中
            editor.commit();
            Toast.makeText(this, "状态保存成功", Toast.LENGTH_SHORT).show();
        }
    }
}

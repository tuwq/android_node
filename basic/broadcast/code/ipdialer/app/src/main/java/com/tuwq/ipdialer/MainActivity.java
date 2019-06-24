package com.tuwq.ipdialer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_prefix;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_prefix = (EditText) findViewById(R.id.et_prefix);
        Button btn_save = (Button) findViewById(R.id.btn_save);

        sp = getSharedPreferences("info", MODE_PRIVATE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prefix = et_prefix.getText().toString().trim();
                if(TextUtils.isEmpty(prefix)){
                    prefix = "";
                }
                // 存储前缀
                sp.edit().putString("prefix", prefix).commit();
                Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

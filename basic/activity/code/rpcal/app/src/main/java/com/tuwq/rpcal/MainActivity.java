package com.tuwq.rpcal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private RadioGroup rg_gender;
    private Button btn_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText("tuwq");
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        btn_calc = (Button) findViewById(R.id.btn_calc);

        btn_calc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString().trim();
        boolean isMale = true;
        int id = rg_gender.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_male:
                isMale = true;
                break;
            case R.id.rb_female:
                isMale = false;
                break;
        }
        Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
        // Bundle extras = new Bundle();
        // 传递对象时需要将对象序列化
        intent.putExtra("name",name);
        intent.putExtra("gender", isMale);
        startActivity(intent);
    }
}

package com.tuwq.android5;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class TextinputlayoutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_textinputlayout);

        this.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TextinputlayoutActivity.this,"我被点击了..",Toast.LENGTH_SHORT).show();
            }
        });
        final TextInputLayout til = (TextInputLayout) findViewById(R.id.til);
        //获取TextInputLayout的输入操作
        EditText editText = til.getEditText();
        //监听输入框的操作
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 10){
                    til.setErrorEnabled(true);//设置是否可以输出错误信息
                    til.setError("输入的文字超过十个");//设置错误信息
                }else{
                    til.setErrorEnabled(false);
                }
            }
        });
    }
}

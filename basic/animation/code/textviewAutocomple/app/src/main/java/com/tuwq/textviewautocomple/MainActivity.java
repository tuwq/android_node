package com.tuwq.textviewautocomple;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {
    private String[] names = {"laowang","laozhang","laoli","xiaowang","xiaozhang","xiaoli"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.item, names);
        actv.setAdapter(adapter);
    }

}

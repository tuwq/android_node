package com.tuwq.sendsms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReplyActivity extends Activity {
    private String[] smss = {"我在睡觉,稍后回复","我在敲代码,稍后回复","我在开会,稍后回复"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        ListView lv_reply = (ListView) findViewById(R.id.lv_reply);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_reply, smss);
        lv_reply.setAdapter(adapter);
        lv_reply.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String result = smss[position];
                Intent data = new Intent();
                data.putExtra("content", result);
                setResult(4,data);
                finish();
            }
        });

    }
}

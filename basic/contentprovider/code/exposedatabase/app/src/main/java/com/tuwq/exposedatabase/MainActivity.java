package com.tuwq.exposedatabase;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends Activity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOpenHelper helper = new MyOpenHelper(this);
        db = helper.getReadableDatabase();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}

package com.tuwq.sqlite;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;


public class MainActivity extends AppCompatActivity {

    private MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);
        helper = new MyHelper(this);
    }

    public void add(View v){
        //存储空间  如果存储满会返回一个可读的db  未满返回的是可写的
        //  helper.getReadableDatabase();
        //如果存储空间满了会crash  未满返回的是可写的
        SQLiteDatabase db = helper.getWritableDatabase("123456");
        db.execSQL("insert into heima values(null,'张三')");
        db.execSQL("insert into heima values(null,'李四')");
    }

    public void query(View v){
        SQLiteDatabase db = helper.getReadableDatabase("123456");
        Cursor cursor = db.query("heima", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            System.out.println("name = "+name);
        }
    }
}

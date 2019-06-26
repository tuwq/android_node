package com.tuwq.exposedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "contentprovidedemo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");
        db.execSQL("insert into info(name,phone) values('张三','13777777')");
        db.execSQL("insert into info(name,phone) values('李四','13888888')");
        db.execSQL("insert into info(name,phone) values('王五','13666666')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

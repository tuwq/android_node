package com.tuwq.sqlite;


import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "heima.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sqlite 自动增长  数据类型必须是 Integer 自动增长是 primary key
        db.execSQL("create table heima(_id Integer primary key,name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


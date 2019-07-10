package com.tuwq.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberOpenHelper extends SQLiteOpenHelper {
    //设置数据库的信息，创建数据库
    //name : 数据库的名称
    //factory : 游标工厂
    //version : 数据库的版本
    public BlackNumberOpenHelper(Context context) {
        super(context, BlackNumberConstants.DB_NAME, null, BlackNumberConstants.DB_VERSION);
    }
    //创建表结构
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BlackNumberConstants.DB_SQL);
    }
    //更新数据库的时候调用的方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

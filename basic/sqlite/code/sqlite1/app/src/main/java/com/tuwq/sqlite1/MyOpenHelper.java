package com.tuwq.sqlite1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {


    public MyOpenHelper(Context context) {
        /**
         * arg1: 上下文
         * arg2: 数据库名
         * arg3: 游标工厂
         * arg4: 数据库版本号
         */
        super(context, "demo.db", null, 1);
    }

    /**
     * 当数据库首次创建时,会调用这个方法,在这个方法中,我们一般做表结构的创建和数据初始化操作
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");
        System.out.println("onCreate被调用");
    }

    /**
     * 表结构的修改,sqlite只能添加字段
     * 当数据库版本号升级后调用,如1变为2
     * 根据版本号不同做出不同的数据库结构升级
     * @param db
     * @param oldVersion 旧的版本号
     * @param newVersion 新的版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table info add age integer");
        System.out.println("onUpgrade被调用  oldVersion"+oldVersion+"newVersion"+newVersion);
        switch (oldVersion) {
            case 2:
                break;
            default:
                break;
        }
    }

    /**
     * 当数据库版本降级时调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("onDowngrade被调用");
    }
}

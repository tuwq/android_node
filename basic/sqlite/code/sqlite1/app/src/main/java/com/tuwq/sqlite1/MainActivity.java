package com.tuwq.sqlite1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new MyOpenHelper(this);
        /**
         * 创建打开一个数据库,获得的都是可读可写的数据库
         * 当磁盘满的时候getReadableDatabase返回一个只读的数据库,而getWritableDatabase会出错
         */
        // myOpenHelper.getWritableDatabase();
        SQLiteDatabase database = openHelper.getReadableDatabase();
    }

    public void insert2(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String nullColumnHack = null;
        ContentValues values = new ContentValues();
        values.put("name", "lisi");
        values.put("phone", "13777777777");
        values.put("age", 25);
        long id = database.insert("info", nullColumnHack, values);
        if(id != -1){
            Toast.makeText(this, "插入成功:"+id, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

    public void delete2(View v) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String table = "info";
        String whereClause = "name = ?";
        String[] whereArgs = {"lisi"};
        int delete = database.delete(table, whereClause, whereArgs);
        Toast.makeText(this, "删除结果:"+delete+"-", Toast.LENGTH_SHORT).show();
        database.close();
    }

    public void update2(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", "110");

        int update = database.update("info", values, "name = ?", new String[]{"lisi"});
        Toast.makeText(this, "¸修改结果:"+update+"-", Toast.LENGTH_SHORT).show();
        database.close();
    }

    public void query2(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query("info", new String[]{"name","phone"}, "name = ?", new String[]{"lisi"}, null, null, null, null);
        while(cursor.moveToNext()){
            String phone = cursor.getString(1);
            System.out.println("into" + phone);
            Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        database.close();
    }

    public void insert(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "insert into info (name,phone,age) values('zhangsan','13888888888',30)";
        database.execSQL(sql);
        sql = "insert into info (name,phone,age) values('lisi','13999999999',20)";
        database.execSQL(sql);
        database.close();
    }

    public void query(View v) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String sql = "select * from info";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            System.out.println("name=" + name + "phone=" + phone);
        }
        cursor.close();
        database.close();
    }

    public void delete(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "delete from info where name = 'zhangsan'";
        database.execSQL(sql);
        sql = "delete from info where name = 'lisi'";
        database.execSQL(sql);
        database.close();
    }
    public void update(View v) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String sql = "update info set phone='1234567' where name ='zhangsan'";
        database.execSQL(sql);
        database.close();
    }

}

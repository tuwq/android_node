package com.tuwq.accessotherdatabase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void query(View v){
//	    String path = "/data/data/com.tuwq.exposedatabase/databases/tuwq.db";
//	    SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
// 	    Cursor cursor = database.rawQuery("select * from info", null);
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.tuwq.provider/query");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            System.out.println("name="+name+"phone"+phone);
        }
    }

    public void insert(View v){
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://com.tuwq.provider/insert");
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("phone", "12345678");
        Uri insert = resolver.insert(url, values);
        System.out.println(insert);
    }

    public void update(View v){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.tuwq.provider/update");
        ContentValues values = new ContentValues();
        values.put("phone", "110");
        String where="name = ?";
        String[] selectionArgs = {"张三"};
        int update = resolver.update(uri, values, where, selectionArgs);
        Toast.makeText(getApplicationContext(), "¸更新"+update+"成功", Toast.LENGTH_SHORT).show();
    }

    public void delete(View v){
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://com.tuwq.provider/delete");
        String where = "name = ?";
        String[] selectionArgs = {"张三"};
        int delete = resolver.delete(url, where, selectionArgs);
        Toast.makeText(getApplicationContext(), "删除"+delete+"成功", Toast.LENGTH_SHORT).show();
    }
}

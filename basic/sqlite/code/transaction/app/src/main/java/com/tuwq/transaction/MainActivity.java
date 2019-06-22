package com.tuwq.transaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new MyOpenHelper(this);
    }

    public void transact(View v){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        // 开启事务
        db.beginTransaction();
        try {
            // 开始转账
            db.execSQL("update info set money= money-200 where name=?",new String[]{"张三"});
            int i = 100/0;
            db.execSQL("update info set money= money+200 where name=?",new String[]{"李四"});
            // 事务操作执行完毕后调用
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(this, "转账异常(18876)", Toast.LENGTH_SHORT).show();
        }finally {
            // 结束事务
            db.endTransaction();
            db.close();
        }
    }

    public void findInfo(View v) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String sql = "select * from info";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            System.out.println("name=" + name + "phone=" + phone + "money=" + money);
        }
        cursor.close();
        database.close();
    }
}

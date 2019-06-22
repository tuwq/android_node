package com.tuwq.listview3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuwq.listview3.bean.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyOpenHelper openHelper;
    ListView lv_list;
    private ArrayList<Person> persons = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new MyOpenHelper(this);
        lv_list = this.findViewById(R.id.lv_list);
    }

    public void insert(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        database.execSQL("insert into info(name,phone) values('zhangsan','13777777');");
        database.execSQL("insert into info(name,phone) values('lisi','13888888');");
        database.execSQL("insert into info(name,phone) values('wangwu','139999999');");
        database.close();
        Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show();
    }

    public void query(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from info", null);
        while(cursor.moveToNext()){
            Person person = new Person();
            person.name = cursor.getString(1);
            person.phone = cursor.getString(2);
            persons.add(person);
        }
        cursor.close();
        database.close();
        for(Person person:persons){
            System.out.println(person);
        }
        lv_list.setAdapter(new MyAdapter());
    }

    public void clear(View v) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String table = "info";
        String whereClause = "name = ?";
        String[] whereArgs = {"zhangsan"};
        int delete = database.delete(table, whereClause, whereArgs);
        whereArgs[0] = "lisi";
        database.delete(table, whereClause, whereArgs);
        whereArgs[0] = "wangwu";
        database.delete(table, whereClause, whereArgs);
        Toast.makeText(this, "删除结果:"+delete+"-", Toast.LENGTH_SHORT).show();
        persons.clear();
        Toast.makeText(this, "清除数据成功", Toast.LENGTH_SHORT).show();
        database.close();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return persons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            Person person = persons.get(position);
            tv_name.setText(person.name);
            tv_phone.setText(person.phone);
            return view;
        }
    }
}

package com.tuwq.savecontact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et_address;
    private EditText et_name;
    private EditText et_email;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_address = (EditText) findViewById(R.id.et_address);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
    }


    public void save(View v){
        ContentResolver resolver = getContentResolver();
        Uri raw_contact_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = resolver.query(raw_contact_uri, new String[]{"contact_id"}, null, null, null);
        int count = cursor.getCount();

        ContentValues values = new ContentValues();
        values.put("contact_id", count+1);
        resolver.insert(raw_contact_uri, values);

        ContentValues add_values = new ContentValues();
        String address = et_address.getText().toString();
        add_values.put("raw_contact_id", count+1);
        add_values.put("data1", address);
        add_values.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
        resolver.insert(data_uri, add_values);

        ContentValues name_values = new ContentValues();
        String name = et_name.getText().toString();
        name_values.put("raw_contact_id", count+1);
        name_values.put("data1", name);
        name_values.put("mimetype", "vnd.android.cursor.item/name");
        resolver.insert(data_uri, name_values);

        ContentValues phone_values = new ContentValues();
        String phone = et_phone.getText().toString();
        phone_values.put("raw_contact_id", count+1);
        phone_values.put("data1", phone);
        phone_values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        resolver.insert(data_uri, phone_values);

        ContentValues email_values = new ContentValues();
        String email = et_email.getText().toString();
        email_values.put("raw_contact_id", count+1);
        email_values.put("data1", email);
        email_values.put("mimetype", "vnd.android.cursor.item/email_v2");
        resolver.insert(data_uri, email_values);
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }

}

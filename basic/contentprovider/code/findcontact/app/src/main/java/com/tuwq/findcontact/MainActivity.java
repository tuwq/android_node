package com.tuwq.findcontact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void queryContact(View v){
        ContentResolver resolver = getContentResolver();
        Uri raw_contact_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = resolver.query(raw_contact_uri, new String[]{"contact_id"}, null, null, null);
        while(cursor.moveToNext()){
            // 查询联系人id
            String id = cursor.getString(0);
            System.out.println("id"+id);
            String[] projection ={"data1","mimetype"};
//			Cursor cursor2 = resolver.query(data_uri, null, "raw_contact_id=?", new String[]{id}, null);
//			cursor2.moveToNext();
//			int columnCount = cursor2.getColumnCount();
//			for(int i = 0;i<columnCount;i++){
//				String columnName = cursor2.getColumnName(i);
//				if(columnName.contains("mime")){
//					System.out.println(columnName);
//				}
//			}
            Contact contact = new Contact();
            // 通过联系人id查找联系人信息
            Uri data_uri = Uri.parse("content://com.android.contacts/data");
            Cursor cursor2 = resolver.query(data_uri, projection, "raw_contact_id=?", new String[]{id}, null);
            while(cursor2.moveToNext()){
                String result = cursor2.getString(0);
                String type = cursor2.getString(1);
                System.out.println(result+"====="+type);
                // 内容信息
                if("vnd.android.cursor.item/phone_v2".equals(type)){
                    contact.phone = result;
                }else if("vnd.android.cursor.item/email_v2".equals(type)){
                    contact.email = result;
                }else if("vnd.android.cursor.item/name".equals(type)){
                    contact.name = result;
                }else if("vnd.android.cursor.item/postal-address_v2".equals(type)){
                    contact.address = result;
                }
            }
            contacts.add(contact);
        }
        for(Contact contact:contacts){
            System.out.println(contact);
        }
    }
}

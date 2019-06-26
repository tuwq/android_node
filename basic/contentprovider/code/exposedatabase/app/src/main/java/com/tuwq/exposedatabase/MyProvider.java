package com.tuwq.exposedatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider {

    private MyOpenHelper openHelper;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int QUERY_SUCESS = 0;
    private static final int INSERT_MATCHED = 1;
    private static final int UPDATE_MATCHED = 2;
    private static final int DELETE_MATCHED = 3;

    static
    {
        // 给当前的URL匹配器添加一个匹配规则
        sURIMatcher.addURI("com.tuwq.provider", "query", QUERY_SUCESS);
        sURIMatcher.addURI("com.tuwq.provider", "insert", INSERT_MATCHED);
        sURIMatcher.addURI("com.tuwq.provider", "update", UPDATE_MATCHED);
        sURIMatcher.addURI("com.tuwq.provider", "delete", DELETE_MATCHED);
        //sURIMatcher.addURI("com.tuwq.provider", "student", 5);
    }

    @Override
    public boolean onCreate() {
        openHelper = new MyOpenHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int result = sURIMatcher.match(uri);
        if(result == INSERT_MATCHED){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            long insert = db.insert("info", null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(String.valueOf(insert));
        }else{
            return null;
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int result = sURIMatcher.match(uri);
        if(result == QUERY_SUCESS){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }else{
            throw new IllegalStateException("口令错误");
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = sURIMatcher.match(uri);
        if(result == DELETE_MATCHED){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int delete = db.delete("info", selection, selectionArgs);
            return delete;
        }else{
            return -1;
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = sURIMatcher.match(uri);
        if(result==UPDATE_MATCHED){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int update = db.update("info", values, selection, selectionArgs);
            db.close();
            return update;
        }else{
            return -1;
        }

    }

}

package com.tuwq.mobileplayer.db;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import com.tuwq.mobileplayer.adapter.VBangAdapter;
import com.tuwq.mobileplayer.utils.LogUtils;

public class MyAsyncQueryHandler extends AsyncQueryHandler {
    private static final String TAG = "MyAsyncQueryHandler";

    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    /**
     * 异步查询获取到的结果
     * @param token
     * @param cookie
     * @param cursor
     */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        LogUtils.e(TAG,"MyAsyncQueryHandler.onQueryComplete,获取到cursor数据");
        VBangAdapter adapter = (VBangAdapter) cookie;
        adapter.swapCursor(cursor);
    }
}

package com.tuwq.mobileplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.widget.CursorAdapter;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.Util;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VBangAdapter extends CursorAdapter {

    public VBangAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public VBangAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public VBangAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * 创建新的item,初始化holder
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_music, null);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return view;
    }

    /**
     * 填充item内容
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String title = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
        title = Util.formatName(title);
        holder.tvTitle.setText(title);
        holder.tvArtist.setText(cursor.getString(cursor.getColumnIndex(Media.ARTIST)));
        long size = cursor.getLong(cursor.getColumnIndex(Media.SIZE));
        holder.tvSize.setText(Formatter.formatFileSize(context,size));
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_artist)
        TextView tvArtist;
        @Bind(R.id.tv_size)
        TextView tvSize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

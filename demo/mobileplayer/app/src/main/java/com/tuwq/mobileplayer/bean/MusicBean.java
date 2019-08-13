package com.tuwq.mobileplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

import com.tuwq.mobileplayer.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicBean implements Serializable {
    public String title;
    public String path;
    public long size;
    public String artist;

    public static MusicBean fromCursor(Cursor cursor){
        MusicBean musicBean=new MusicBean();
        String name=cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
        musicBean.title= Util.formatName(name);
        musicBean.path=cursor.getString(cursor.getColumnIndex(Media.DATA));
        musicBean.size=cursor.getLong(cursor.getColumnIndex(Media.SIZE));
        musicBean.artist=cursor.getString(cursor.getColumnIndex(Media.ARTIST));
        return  musicBean;
    }

    public static ArrayList<MusicBean> listFromCursor(Cursor cursor){
        ArrayList<MusicBean> beanArrayList = new ArrayList<>();

        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            MusicBean musicBean = fromCursor(cursor);
            beanArrayList.add(musicBean);
        }

        return beanArrayList;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", artist='" + artist + '\'' +
                '}';
    }
}

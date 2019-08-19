package com.tuwq.mobilesafe.db.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDao {
	
	/**
	 * 查询应用程序是否是病毒
	 * md5 : 应用程序特征码的md5值
	 *@return
	 */
	public static boolean isAntivirus(Context context,String md5){
		boolean isAntiVirus=false;
		File file = new File(context.getFilesDir(), "antivirus.db");
		SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.query("datable", new String[]{"md5"}, "md5=?", new String[]{md5}, null, null, null);
		if (cursor.moveToNext()) {
			isAntiVirus = true;
		}
		cursor.close();
		database.close();
		return isAntiVirus;
	} 
	
}

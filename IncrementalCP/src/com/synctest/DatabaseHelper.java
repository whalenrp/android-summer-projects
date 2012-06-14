package com.synctest;

import java.lang.Exception;
import java.util.ArrayList;
import android.content.ContentProvider;
import android.text.TextUtils;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "my_games.db";

	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='my_games'", null);

		try{
			if (c.getCount() == 0){
				db.execSQL("CREATE TABLE my_games (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, difficulty TEXT);");
				ContentValues cv = new ContentValues();

				/*cv.put(TestProvider.Contract.TITLE, "God of War");
				cv.put(TestProvider.Contract.DIFFICULTY, "Varying");
				db.insert("my_games", TestProvider.Contract.TITLE, cv);*/
			}
		}finally{
			c.close();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS my_games");
		onCreate(db);
	}

}

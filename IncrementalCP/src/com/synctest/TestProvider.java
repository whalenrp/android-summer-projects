
package com.synctest;

import java.util.ArrayList;
import android.content.ContentProvider;
import android.text.TextUtils;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.content.ContentProviderOperation;
import android.content.UriMatcher;
import android.content.ContentUris;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.net.Uri;



public class TestProvider extends ContentProvider {

	private static final int GAMES=1;
	private static final int GAMES_ID=2;
	private static final UriMatcher MATCHER;
	private static final String TABLE = "my_games";
	private DatabaseHelper db = null;
	
	public static final class Contract implements BaseColumns{
		public static final String AUTHORITY = "com.synctest.TestProvider";
		public static final Uri CONTENT_URI=
			Uri.parse("content://com.synctest.TestProvider/my_games");
		public static final String DEFAULT_SORT_ORDER="title";
		public static final String TITLE="title";
		public static final String DIFFICULTY="difficulty";
		public static final String _ID = "_id";
	}

	static{
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI("com.synctest.TestProvider", "my_games", GAMES);
		MATCHER.addURI("com.synctest.TestProvider", "my_games/#", GAMES_ID);
	}
	
    @Override
    public boolean onCreate()
    {
		db = new DatabaseHelper(getContext());
		return ((db == null)? false:true);
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs){
		int count = db.getWritableDatabase().delete(TABLE, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, 
			String[] selectionArgs, String sort)
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE);

		String orderBy;
		if (TextUtils.isEmpty(sort))
			orderBy = Contract.DEFAULT_SORT_ORDER;
		else
			orderBy = sort;

		Cursor c = qb.query(db.getReadableDatabase(), projection, selection, 
				selectionArgs, null, null, orderBy);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public Uri insert(Uri url, ContentValues initialValues){
		long rowID = 
			db.getWritableDatabase().insert(TABLE, Contract.TITLE, initialValues);

		if (rowID > 0){
			Uri uri = ContentUris.withAppendedId(Contract.CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row int " + url);
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs)
	{
		int count = db.getWritableDatabase().update(TABLE, values, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	@Override
	public String getType(Uri url){
		if (isCollectionUri(url))
			return("vnd.com.synctest.cursor.dir/my_games");

		return ("vnd.com.synctest.cursor.item/my_games");
	}

	private boolean isCollectionUri(Uri url){
		return(MATCHER.match(url) == GAMES);
	}

}

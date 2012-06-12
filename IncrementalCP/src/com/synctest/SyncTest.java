package com.synctest;

import android.app.ListActivity;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract;
import android.app.LoaderManager;

public class SyncTest extends ListActivity 
	implements LoaderManager.LoaderCallbacks<Cursor>
{
	private String[] projection;
	private String numberField = ContactsContract.CommonDataKinds.Phone.NUMBER;
	private String nameField = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
	private SimpleCursorAdapter mAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		projection = new String[]{
			ContactsContract.CommonDataKinds.Phone._ID, 
			ContactsContract.CommonDataKinds.Phone.NUMBER, 
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, 
			};

		//	retrieveData();
		mAdapter = new SimpleCursorAdapter(this, R.layout.rowlayout, null, 
			new String[]{nameField, numberField}, 
			new int[]{R.id.name, R.id.number}, 0);
		setListAdapter(mAdapter);

		getLoaderManager().initLoader(0, null, this);
    }

	/*
	// Edit the contact
	@Override
	protected void onListItemClick( ListView l, View v, int position, long id){
		
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.addContact:
				// Start new activity to add new contact
				Intent intent = new Intent(this, ContactAdder.class);
				startActivity(intent);
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	// Creates a CursorLoader that will monitor the data
	public Loader<Cursor> onCreateLoader(int id, Bundle args){
		return new CursorLoader(this, 
			ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
			projection, 
			null, 
			null, 
			nameField);
	}
	
	public void onLoadFinished(Loader<Cursor> loader, Cursor data){
		mAdapter.swapCursor(data);
	}

	// Called when last cursor is about to be closed.
	// The framework takes care of closing the old cursor, so just swap with null.
	public void onLoaderReset(Loader<Cursor> loader){
		mAdapter.swapCursor(null);
	}
}

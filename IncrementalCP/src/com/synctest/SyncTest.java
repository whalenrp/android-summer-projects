package com.synctest;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.provider.ContactsContract;

public class SyncTest extends ListActivity
{
	private Cursor mData;
	private String[] projection;
	private String nameField = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
	private String sortOrder = nameField;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		retrieveData();
		setListAdapter(new SimpleCursorAdapter(this, R.layout.rowlayout, mData, 
			new String[]{nameField}, new int[]{R.id.name}));
    }

	// Takes care of filling the Cursor mData with whatever date the
	// ContentResolver returns
	private void retrieveData(){
		projection = new String[]{ContactsContract.Contacts._ID, nameField};
		mData = getContentResolver().query(
			ContactsContract.Contacts.CONTENT_URI, // URI for contacts table
			projection,	// list of columns to return
			null, // sql WHERE
			null, //Selection Args
			sortOrder);	
	}
}

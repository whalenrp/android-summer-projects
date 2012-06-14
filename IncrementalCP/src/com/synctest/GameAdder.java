package com.synctest;

import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentResolver;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.content.ContentValues;
import java.util.ArrayList;
import java.lang.Exception;
import android.content.ContentProviderOperation;

public class GameAdder extends Activity 
{
	private EditText title;
	private EditText difficulty;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_adder);

		title = (EditText)findViewById(R.id.title);
		difficulty = (EditText)findViewById(R.id.difficulty);
	}

	// Called when the Add Item button is clicked
	public void addToList(View view){
		String titleStr = title.getText().toString();
		String difficultyStr = difficulty.getText().toString();

		if (titleStr == null || difficultyStr == null) return;

		ArrayList<ContentProviderOperation> ops = 
			new ArrayList<ContentProviderOperation>();

		// Add name field 
		ops.add(ContentProviderOperation.newInsert(
			TestProvider.Contract.CONTENT_URI)
			.withValue(TestProvider.Contract.TITLE, 
				titleStr)
			.withValue(TestProvider.Contract.DIFFICULTY, 
				difficultyStr)
			.build());

		try{
			getContentResolver().applyBatch(TestProvider.Contract.AUTHORITY, ops);
			Toast.makeText(this, "Game Added", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			e.printStackTrace();
		}
		finish();
	}
}

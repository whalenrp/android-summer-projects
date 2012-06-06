package com.example;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MyActivity extends ListActivity
{
    private File curDir;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState==null)
            curDir = new File("/");
        else
            curDir = new File(savedInstanceState.getString("curDir"));
        setListAdapter(new FileAdapter());

    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        bundle.putString("curDir", curDir.toString());
    }

    public void onListItemClick(ListView parent, View v, int position, long id){
        String file = (String)getListAdapter().getItem(position);
        File newDir = new File(curDir.getAbsolutePath(), file);
        if (newDir.isDirectory() && newDir.list()!= null){
            if (newDir.list().length > 0){
                curDir=newDir;
                setListAdapter(new FileAdapter());
            }
        }else if(newDir.isFile()){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(newDir), "text/plain");
            startActivity(intent);
        }
    }

    public void onBackPressed(){
        if (curDir.toString().equals("/")){
            super.onBackPressed();
        }else{
            curDir=curDir.getParentFile();
            setListAdapter(new FileAdapter());
        }
    }

    class FileAdapter extends ArrayAdapter<String>{
        FileAdapter(){
            super(MyActivity.this, R.layout.row, R.id.label, curDir.list());
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View row = super.getView(position, convertView, parent);
            if (curDir.list()!=null){
                TextView textView = (TextView)row.findViewById(R.id.label);
                File current_file = new File(curDir, (String)getListAdapter().getItem(position));
                if (current_file.isDirectory()){
                    textView.setTextColor(Color.CYAN);
                }
            }
            return row;
        }
    }

}



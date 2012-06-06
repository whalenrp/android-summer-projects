package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.net.Socket;

public class SocketClient extends Activity
{
    private Button button;
    private TextView textView;
    private EditText et;
    private DataInputStream input;
    private PrintStream output;
    private Socket socket;
    private String serverIpAddress = "129.59.69.68";
    private static final int REDIRECTED_SERVERPORT = 5567;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (Button)findViewById(R.id.myButton);
        textView = (TextView)findViewById(R.id.myTextView);
        et = (EditText)findViewById(R.id.EditText01);
        try{
            socket = new Socket(serverIpAddress, REDIRECTED_SERVERPORT);
            input = new DataInputStream(socket.getInputStream());
            output = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    output.println(et.getText().toString());
                    textView.setText(textView.getText().toString()
                            + et.getText().toString() + "\n" + input.readLine());
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        });
    }
}

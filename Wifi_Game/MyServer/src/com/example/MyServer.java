package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer extends Activity
{
    ServerSocket ss = null;
    String mClientMsg = "";
    Thread mCommsThread = null;
    protected static final int MSG_ID = 0x1337;
    public static final int SERVERPORT = 6000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView)findViewById(R.id.TextView01);
        tv.setText("Nothing from client yet");
        this.mCommsThread = new Thread(new CommsThread());
        this.mCommsThread.start();
    }

    protected void onStop(){
        super.onStop();
        try{
            ss.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    Handler myUpdateHandler = new Handler(){
         public void handleMessage(Message msg){
             switch (msg.what){
                 case MSG_ID:
                     TextView tv = (TextView)findViewById(R.id.TextView01);
                     tv.setText(mClientMsg);
                 default:
                     break;
             }
             super.handleMessage(msg);
         }
    };

    class CommsThread implements Runnable{
        public void run(){
            Socket socket = null;
            try{
                ss = new ServerSocket(SERVERPORT);
            }catch(IOException e){
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()){
                Message m = new Message();
                m.what = MSG_ID;
                try{
                    if (socket == null)
                        socket = ss.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String st = null;
                    st = input.readLine();
                    mClientMsg = st;
                    myUpdateHandler.sendMessage(m);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

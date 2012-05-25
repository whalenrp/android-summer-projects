package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: richard
 * Date: 5/25/12
 * Time: 8:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (extras!= null){
            Object[] pdus = (Object[])extras.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i< msgs.length; ++i){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str+= msgs[i].getMessageBody().toString();
                str+= "\n";
            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }

    }
}

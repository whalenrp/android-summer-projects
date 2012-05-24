package com.example;

import android.app.Activity;
import android.app.PendingIntent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

public class SmsGame extends Activity
{
    RadioGroup choice;
    Button submit;
    EditText number;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        choice = (RadioGroup)findViewById(R.id.choice);
        submit = (Button)findViewById(R.id.submit);
        number = (EditText)findViewById(R.id.number);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = number.getText().toString();
                String data = "";
                switch (choice.getCheckedRadioButtonId() ){
                    case R.id.rock:
                        data = "r";
                        break;
                    case R.id.paper:
                        data = "p";
                        break;
                    case R.id.scissors:
                        data="s";
                        break;
                    default:
                }
                if (!data.equals("") && phoneNo.length()>0){
                    sendSMS(phoneNo, data);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Please choose your weapon and opponent", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    private void sendSMS(String phoneNo, String data){
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SmsGame.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, data, pendingIntent, null);

    }
}

package com.app.fslo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button accessbutton;
    private Button logoutbutton;
    private ImageButton imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise variable
        accessbutton=(Button) findViewById(R.id.accessbtn);
        logoutbutton=(Button) findViewById(R.id.logout);
        imagebutton=(ImageButton) findViewById(R.id.imgbtn);

        //accessbutton on click method from here
        accessbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, otp.class));

                //send warning sms
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                        sendSMS();
                        //sendSMS1();
                    }
                    else{
                        requestPermissions(new String[]{ Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });


        //logout method

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        //call suppport


    }


    //send access request warning
    private void sendSMS() {
        String phno="+919061017380";
        String phno1="+919207807971";
        String message="WARNING!! LOCKER ACCESS REQUESTED!!";
        String message1="WARNING!! LOCKER ACCESS REQUESTED!!";
        //call sms manager
        try{
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(phno,null,message,null,null);
            smsManager.sendTextMessage(phno1,null,message1,null,null);
            Toast.makeText(this, "Verifying......", Toast.LENGTH_SHORT).show();
            //alphatxt.setText("");

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error in Verifying", Toast.LENGTH_SHORT).show();
            //alphatxt.setText("");

        }

    }


}
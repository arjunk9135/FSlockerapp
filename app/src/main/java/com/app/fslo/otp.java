package com.app.fslo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class otp extends AppCompatActivity {
    private EditText alphatxt;
    private EditText sample;
    public Button verifybtton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //initialise variables
        alphatxt= (EditText) findViewById(R.id.alphacode);
        verifybtton=(Button) findViewById(R.id.verifybtn);
        sample=(EditText)findViewById(R.id.sampletxt);



        //verify button action
        verifybtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }



    //sms function
    private void sendSMS() {
      String phno="+919061017380";
      String phno1="+919061120673";
      String message=alphatxt.getText().toString().trim();
      String message1=alphatxt.getText().toString().trim();
                // crypto


        try{
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(phno,null,message,null,null);
            smsManager.sendTextMessage(phno1,null,message1,null,null);
            Toast.makeText(this, "Verifying....", Toast.LENGTH_SHORT).show();
            //encryption from here......proceed with caution
            char[] ch = new char[message.length()];
            for (int i = 0; i < message.length(); i++) {
                ch[i] = message.charAt(i);
            }

            //for (int j = 0; j < message.length(); j++) {
                //sample.append(""+ch[j]);
            //}



            int[] arr1={78,87,34,43};
            for (Character c : ch)
            {
                sample.append(""+c);
                //System.out.println( (char)(c + 0) );
            }



            //alphatxt.setText("");
           // startActivity(new Intent(otp.this, MainActivity.class));
            Toast.makeText(this, "Access will be verified and granted..Please Wait..", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error in Verifying", Toast.LENGTH_SHORT).show();
            alphatxt.setText("");
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }

    }
    // crypto class




    //send access log


}
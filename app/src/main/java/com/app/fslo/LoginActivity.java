package com.app.fslo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Calendar;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

    //connect to db
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fslo-f1ac8-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //firebase push service get token

        //start
        final EditText email = findViewById(R.id.emailid);
        final EditText password = findViewById(R.id.pwd);
        final Button loginbtn = findViewById(R.id.loginbtn);
        final EditText eid = findViewById(R.id.employeeid);
        final Button signbtn1=(Button) findViewById(R.id.singingnewuser);
        Date currentTime = Calendar.getInstance().getTime();


        //firebase cloud token registration;



        //login btn onclick methods from here
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String emailid = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                final String eidd = eid.getText().toString().trim();
                final String datetime=currentTime.toString();
                if(emailid.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter an email!!", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter a password!!", Toast.LENGTH_SHORT).show();
                }
                else if(eidd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter Emp Id!!", Toast.LENGTH_SHORT).show();
                }
                //check login condition and provide access
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //checks duplicate
                            if(snapshot.hasChild(eidd)){
                                final String  getpwd = snapshot.child(eidd).child("password :").getValue(String.class);
                                if (getpwd.equals(pass)) {
                                    Toast.makeText(LoginActivity.this, "Succesfully Signed in", Toast.LENGTH_SHORT).show();
                                    databaseReference.child("LOGIN ATTEMPT").child(datetime).child("id :").setValue(eidd);
                                    databaseReference.child("LOGIN ATTEMPT").child(datetime).child("email :").setValue(emailid);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong password!!", Toast.LENGTH_SHORT).show();


                                    //open main screen
                                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                databaseReference.child("UnAuthorized Access").child(datetime).child("id :").setValue(eidd);
                                databaseReference.child("UnAuthorized Access").child(datetime).child("email :").setValue(emailid);
                                databaseReference.child("UnAuthorized Access").child(datetime).child("password :").setValue(pass);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }
        });

        //sign up
        signbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
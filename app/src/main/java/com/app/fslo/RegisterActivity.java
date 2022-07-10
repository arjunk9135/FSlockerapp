package com.app.fslo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
//create db reference class

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fslo-f1ac8-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
 // initialsing variables
        final EditText fullname = findViewById(R.id.nameid);
        final EditText email1 = findViewById((R.id.emailid1));
        final EditText eid = findViewById(R.id.empid);
        final EditText password=findViewById(R.id.pwd1);
        final Button singin=(Button) findViewById(R.id.signinbtn);

        // sign in button action
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullnametxt = fullname.getText().toString().trim();
                final String emailtxt = email1.getText().toString().trim();
                final String eidtxt = eid.getText().toString().trim();
                final String passwordtxt=password.getText().toString().trim();

                databaseReference.child("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //checks duplicate
                        if (snapshot.hasChild(eidtxt)) {
                            Toast.makeText(RegisterActivity.this, "ID Already Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            //send data to db
                            databaseReference.child("users").child(eidtxt).child("fullname :").setValue(fullnametxt);
                            databaseReference.child("users").child(eidtxt).child("email :").setValue(emailtxt);
                            databaseReference.child("users").child(eidtxt).child("password :").setValue(passwordtxt);
                            //show success message
                            Toast.makeText(RegisterActivity.this, "User Registered! ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //send data to firebase db

            }
        });


    }
}
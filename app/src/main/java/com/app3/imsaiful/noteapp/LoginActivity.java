package com.app3.imsaiful.noteapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private EditText log_name,log_pas;
    private Button log_in,log_signin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log_name=(EditText)findViewById(R.id.uemail);
        log_in=(Button)findViewById(R.id.lgnbtn);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseAuth=FirebaseDatabase.getInstance().getReference().child("Users");
        log_pas=(EditText)findViewById(R.id.upas);
        log_signin=(Button)findViewById(R.id.sgnbtn);
        log_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        login();
                    }
                }

        );
        log_signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    }
                }


        );


    }

    public void login()
    {
        String uname=log_name.getText().toString().trim();
        String upas=log_pas.getText().toString().trim();
        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(upas))
        {

            mAuth.signInWithEmailAndPassword(uname,upas).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                checkUserProfile();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please Enter correct Detail",Toast.LENGTH_LONG).show();
                            }



                        }
                    }
            );

        }


    }

    private void checkUserProfile() {
        final String user_id=mAuth.getCurrentUser().getUid();
        mDatabaseAuth.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(user_id))
                        {
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error by UID",Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}

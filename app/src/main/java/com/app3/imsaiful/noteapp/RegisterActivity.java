package com.app3.imsaiful.noteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText rname,ruemail,rupas;
    private Button rButton;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rname=(EditText)findViewById(R.id.name);
        ruemail=(EditText)findViewById(R.id.uemail);
        rupas=(EditText)findViewById(R.id.upas);
        rButton=(Button)findViewById(R.id.Reg_submit);
        progressBar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        rButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startRegister();
                    }
                }

        );
    }
    public void startRegister()
    {
        final String name=rname.getText().toString().trim();
        String uemail=ruemail.getText().toString().trim();
        String upaswd=rupas.getText().toString().trim();
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(name));
        {
            progressBar.setMessage("Siging in...");
            progressBar.show();
            mAuth.createUserWithEmailAndPassword(uemail,upaswd).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    String uid=mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db=databaseReference.child(uid);
                                    current_user_db.child("name").setValue(name);
                                    current_user_db.child("pic").setValue("Default");
                                    progressBar.dismiss();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                                }

                        }
                    }

            );


        }


    }
}

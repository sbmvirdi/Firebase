package com.example.firebase;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mUsername;
    private EditText mPassword;
    private Button mSigninbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this,AuthenticatedUser.class));
            finish();
        }

       mUsername = findViewById(R.id.username);
        mPassword= findViewById(R.id.password);

        mSigninbutton = findViewById(R.id.signinbutton);

        mSigninbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TextUtils
                String username  = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),AuthenticatedUser.class));
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Fill All the details!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        /*
        *
        *  mSignupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TextUtils
                String username  = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    mAuth.signUnWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Fill All the details!", Toast.LENGTH_SHORT).show();
                }


            }
        });*/

    }
    }


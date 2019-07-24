package com.example.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticatedUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mSign,mupload,mload;
    private ImageView mfetch;
    private static final int GALARY_REQUEST =1;
    private StorageReference mref;
    private DatabaseReference r;
    private String UID,url="demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticated_user);
        mAuth = FirebaseAuth.getInstance();
        mSign = findViewById(R.id.signout);
        mupload = findViewById(R.id.upload);
        mload= findViewById(R.id.load);
        mref = FirebaseStorage.getInstance().getReference();
        mfetch = findViewById(R.id.imagelogo);
        r = FirebaseDatabase.getInstance().getReference().child("users");
        if (mAuth.getCurrentUser()!=null){
            UID = mAuth.getCurrentUser().getUid();
        }



        mload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),ImageLoad.class));
            }
        });

        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallaryintent = new Intent();
                gallaryintent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryintent.setType("image/*");
                startActivityForResult(gallaryintent,GALARY_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode  == GALARY_REQUEST && resultCode == RESULT_OK && data.getData()!= null){
            Uri image = data.getData();
            Toast.makeText(this, ""+image, Toast.LENGTH_SHORT).show();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mfetch.setImageBitmap(bitmap);
            mref.child("images/"+ UID).putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AuthenticatedUser.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });




                    Map<String,String>  m = new HashMap<>();
                    m.put("userid",UID);
                    m.put("name","Shubam Virdi");
                    m.put("designation","Android TA");
                    r.child(UID).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(AuthenticatedUser.this, "Data Written", Toast.LENGTH_SHORT).show();
                         }
                         else {
                             Toast.makeText(AuthenticatedUser.this, "Data Failed", Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }
            }

        }



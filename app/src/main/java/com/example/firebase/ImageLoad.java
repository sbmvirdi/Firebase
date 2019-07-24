package com.example.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ImageLoad extends AppCompatActivity {
    private ImageView i;
    private String url;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        i=findViewById(R.id.imageload);
        mref = FirebaseDatabase.getInstance().getReference().child("imageurl");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                url = (String) dataSnapshot.getValue();
                Toast.makeText(ImageLoad.this, url, Toast.LENGTH_SHORT).show();
                Glide.with(ImageLoad.this).load(url).into(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImageLoad.this, "Cancelled"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }
}

package com.capstonewansook.ideaboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.recyclerview.ExtendedFragment_1Adapter;
import com.capstonewansook.ideaboard.recyclerview.ExtendedFragment_1Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class extededimageActivity extends AppCompatActivity {
    LinearLayout extendedimageLayout;
    String uid;
    String boardid;
    int extendedimages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extededimage);
        extendedimageLayout = findViewById(R.id.extededimageLayout);

        Intent intent = getIntent();
        extendedimages = intent.getExtras().getInt("extededimage");
        uid = intent.getExtras().getString("ideamainuid");
        boardid = intent.getExtras().getString("boardid");

        final FirebaseStorage storage = FirebaseStorage.getInstance();

        for (int i = 0; i < extendedimages; i++) {
            final ImageView imageView = new ImageView(getApplicationContext());
            StorageReference imagefileRef = storage.getReference().child("posts/" + boardid + "/image"+i);
                imagefileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Glide.with(getApplicationContext()).load(task.getResult()).into(imageView);
                            extendedimageLayout.addView(imageView);
                        }
                    }
                });
        }


        extendedimageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ideamainintend = new Intent(getApplicationContext(), IdeamainActivity.class);
                startActivity(ideamainintend);
            }
        });


    }

}

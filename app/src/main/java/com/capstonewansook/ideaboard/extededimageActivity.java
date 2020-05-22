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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class extededimageActivity extends AppCompatActivity {
    LinearLayout extendedimageLayout;
    String uid;
    String boardid;
    int extendedimages;

    ArrayList<String> arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extededimage);
        extendedimageLayout = findViewById(R.id.extededimageLayout);
        Intent intent = getIntent();
        extendedimages = intent.getExtras().getInt("extededimage");
        uid = intent.getExtras().getString("ideamainuid");
        boardid = intent.getExtras().getString("boardid");

        ViewPager viewPager = findViewById(R.id.viewpager);
        ExtendedFragment_1Adapter exdt = new ExtendedFragment_1Adapter(getSupportFragmentManager());
        viewPager.setAdapter(exdt);

        if(extendedimages > 0) {
            ImageDownload();
            for(int i = 0; i< arr.size(); i++){
                ExtendedFragment_1 exfg = new ExtendedFragment_1();
                Bundle bundle = new Bundle();
                bundle.putString("imgRes",arr.get(i));
                exfg.setArguments(bundle);
                exdt.addItem(exfg);
                Log.d("my123",arr.toString());
            }
            exdt.notifyDataSetChanged();
        }
        extendedimageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ideamainintend = new Intent(getApplicationContext(), IdeamainActivity.class);
                startActivity(ideamainintend);
            }
        });




    }
    private void ImageDownload() {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        for (int i = 0; i < extendedimages; i++) {
            StorageReference imagefileRef = storage.getReference().child("posts/" + boardid + "/image" + i);
            imagefileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        arr.add(task.getResult().toString());
                        Log.d("getdown123",arr.toString());
                    }
                }
            });
        }
    }
}

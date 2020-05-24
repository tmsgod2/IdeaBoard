package com.capstonewansook.ideaboard;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Chatalbum extends AppCompatActivity {
    private TextView textView;
    private GridView gridView;
    private ArrayList<ImageView> arrayList;
    private int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatalbum);
        textView = (TextView) findViewById(R.id.albumtext);
        gridView = (GridView) findViewById((R.id.albumGrid));
        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("사진");
        actionBar.setDisplayHomeAsUpEnabled(true);
        String string = getIntent().getExtras().getString("roomid");
        arrayList = new ArrayList<ImageView>();
        int size = getIntent().getExtras().getInt("size");

            FirebaseStorage.getInstance().getReference().child("chatings/" + string).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (StorageReference item : listResult.getItems()) {
                        final ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        item.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Glide.with(getApplicationContext())
                                            .load(task.getResult())
                                            .into(imageView);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    });
                                    arrayList.add(imageView);
                                    textView.setVisibility(View.GONE);
                                }

                                ChatalbumAdapter chatalbumAdapter = new ChatalbumAdapter(getApplicationContext(),arrayList);
                                gridView.setAdapter(chatalbumAdapter);
                            }
                        });
                    }
                }
            });


}

    //상단의 뒤로가기 버튼 클릭시 뒤로 감
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

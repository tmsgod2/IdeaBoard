package com.capstonewansook.ideaboard;

import android.content.Intent;
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
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Chatalbum extends AppCompatActivity {
    private TextView textView;
    private GridView gridView;
    private ArrayList<ChatalbumData> arrayList;
    private int i;
    private String string;
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
        string = getIntent().getExtras().getString("roomid");
        arrayList = new ArrayList<ChatalbumData>();
        int size = getIntent().getExtras().getInt("size");

            FirebaseStorage.getInstance().getReference().child("chatings/" + string).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (final StorageReference item : listResult.getItems()) {
                        final ImageView imageView = new ImageView(getApplicationContext());
                        final ChatalbumData chatalbumData = new ChatalbumData(imageView,0);
                        item.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                chatalbumData.setA((int)storageMetadata.getCreationTimeMillis());
                            }
                        });
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,400));
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
                                            Intent intent = new Intent(getApplicationContext(), Chatalbumclick.class);
                                            intent.putExtra("image", string+"/"+item.getName());
                                            startActivity(intent);
                                        }
                                    });
                                    chatalbumData.setImageView(imageView);

                                    arrayList.add(chatalbumData);
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

package com.capstonewansook.ideaboard;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

public class Chatalbum extends AppCompatActivity {
    private TextView textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatalbum);
        textView = (TextView) findViewById(R.id.albumtext);
        linearLayout = (LinearLayout) findViewById((R.id.albumLinear));
        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("사진");
        actionBar.setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < 3; i++) {
            final ImageView imageView = new ImageView(getApplicationContext());
            imageView.setPadding(10, 10, 10, 10);
            //경로 바까야됨
            FirebaseStorage.getInstance().getReference().child("/posts/9MPhgXDHVPprsP41XaVq/image" + i).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        textView.setVisibility(View.GONE);
                        try {
                            Glide.with(getApplicationContext())
                                    .load(task.getResult())
                                    .into(imageView);
                            linearLayout.addView(imageView);
                        } catch (NullPointerException e) {

                        }
                    }
                }
            });
        }

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

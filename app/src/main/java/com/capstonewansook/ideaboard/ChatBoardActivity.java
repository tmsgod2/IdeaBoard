package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChatBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatboard);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("누구누구 채팅방");

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //상단의 뒤로가기 버튼 클릭시 뒤로 감
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

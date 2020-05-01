package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewData;

import java.util.ArrayList;
import java.util.Date;

public class IdeamainActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView titleTextView;
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ideamain);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("아이디어 게시판 창");

        actionBar.setDisplayHomeAsUpEnabled(true);
        ArrayList<CommentRecyclerViewData> list = new ArrayList<>();
        list.add(new CommentRecyclerViewData("asdasdasd","이앙",new Date(System.currentTimeMillis()),"asdasdasdasdasd"));
        RecyclerViewSet(list, (RecyclerView)findViewById(R.id.ideamain_comment_recyclerView),new CommentRecyclerViewAdapter(list));

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

    private void RecyclerViewSet(ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}

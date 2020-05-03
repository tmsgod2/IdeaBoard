package com.capstonewansook.ideaboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String TAG = "IdeamainActivity";
    ImageView profileImage;
    TextView titleTextView;
    TextView contentTextView;
    CheckBox starCheckBox;
    EditText commentEditText;
    Button commentButton;
    Button commentSubmitButton;
    ImageView commentExitImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ideamain);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("아이디어 게시판 창");

        profileImage = findViewById(R.id.ideamain_profile_imageView);
        titleTextView = findViewById(R.id.ideamain_title_textView);
        contentTextView = findViewById(R.id.ideamain_contents_textView);
        starCheckBox = findViewById(R.id.ideamain_star_checkBox);
        commentEditText = findViewById(R.id.ideamain_comment_editText);
        commentButton = findViewById(R.id.ideamain_comment_button);
        commentSubmitButton = findViewById(R.id.ideamain_comment_submit_button);
        commentExitImageView = findViewById(R.id.ideamain_comment_exit_imageView);

        titleTextView.setText("이런거 한번 생각해봤는데 어떰?");
        contentTextView.setText("asd");


        starCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,starCheckBox.isChecked()+"");
                if(!starCheckBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "추천을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "추천을 하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                KeboardOn();
            }
        });

        commentExitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(view.GONE);
                KeboardOff();
            }
        });
        final ArrayList<CommentRecyclerViewData> list = new ArrayList<>();
        commentSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentEditText.getText().toString();
                if(comment.length()>0&&!comment.equals("")){
                    list.add(new CommentRecyclerViewData(MainActivity.uid, MainActivity.cus.getName(), new Date(System.currentTimeMillis()),comment));
                    RecyclerViewSet((RecyclerView)findViewById(R.id.ideamain_comment_recyclerView),new CommentRecyclerViewAdapter(list));
                    KeboardOff();
                }
            }
        });

        actionBar.setDisplayHomeAsUpEnabled(true);

        list.add(new CommentRecyclerViewData("asdasdasd","이앙",new Date(System.currentTimeMillis()),"asdasdasdasdasd"));
        RecyclerViewSet((RecyclerView)findViewById(R.id.ideamain_comment_recyclerView),new CommentRecyclerViewAdapter(list));


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

    private void RecyclerViewSet(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    public void KeboardOn(){
        commentEditText.setVisibility(View.VISIBLE);
        commentSubmitButton.setVisibility(View.VISIBLE);
        commentExitImageView.setVisibility(View.VISIBLE);
        commentEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void KeboardOff(){
        commentEditText.setText("");
        commentEditText.setVisibility(View.GONE);
        commentSubmitButton.setVisibility(View.GONE);
        commentButton.setVisibility(View.VISIBLE);
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}

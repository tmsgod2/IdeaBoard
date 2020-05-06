package com.capstonewansook.ideaboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class IdeamainActivity extends AppCompatActivity {

    private static final String TAG = "IdeamainActivity";
    IdeaMainData mainData;
    String uid;
    String boardId;
    int star;
    ImageView profileImage;
    TextView titleTextView;
    TextView contentTextView;
    CheckBox starCheckBox;
    TextView starTextView;
    ImageView chatImageView;
    EditText commentEditText;
    Button commentButton;
    Button commentSubmitButton;
    ImageView commentExitImageView;
    LinearLayout imageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ideamain);

        mainData = (IdeaMainData)getIntent().getSerializableExtra(IdeaMainIn.IDEAMAIN_KEY);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("아이디어 게시판 창");

        profileImage = findViewById(R.id.ideamain_profile_imageView);
        titleTextView = findViewById(R.id.ideamain_title_textView);
        contentTextView = findViewById(R.id.ideamain_contents_textView);
        starCheckBox = findViewById(R.id.ideamain_star_checkBox);
        starTextView = findViewById(R.id.ideamain_star_textView);
        chatImageView = findViewById(R.id.ideamain_chat_imageView);
        commentEditText = findViewById(R.id.ideamain_comment_editText);
        commentButton = findViewById(R.id.ideamain_comment_button);
        commentSubmitButton = findViewById(R.id.ideamain_comment_submit_button);
        commentExitImageView = findViewById(R.id.ideamain_comment_exit_imageView);
        imageLayout = findViewById(R.id.ideamain_image_linearlayout);
        boardId = mainData.boardId;
        uid = mainData.uid;
        titleTextView.setText(mainData.title);
        contentTextView.setText(mainData.content);
        star = mainData.stars;

        profileImage.setBackground(new ShapeDrawable((new OvalShape())));
        profileImage.setClipToOutline(true);
        starTextView.setText(String.valueOf(mainData.stars));
        starCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,starCheckBox.isChecked()+"");
                if(!starCheckBox.isChecked()){
                    star--;
                    Toast.makeText(getApplicationContext(), "추천을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    starTextView.setText(String.valueOf(star));
                }
                else{
                    Toast.makeText(getApplicationContext(), "추천을 하셨습니다.", Toast.LENGTH_SHORT).show();
                    star++;
                    starTextView.setText(String.valueOf(star));
                }

            }
        });



        chatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatBoardActivity.class);
                startActivity(chatIntent);

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

        if(mainData.imgLength>0)
            ImageDownload();

        setProfileImage();

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

    //댓글쓰기 버튼 클릭했을 때 에디트 텍스트랑 댓글 등록 버튼 활성화 밑 키보드 활성화
    private void KeboardOn(){
        commentEditText.setVisibility(View.VISIBLE);
        commentSubmitButton.setVisibility(View.VISIBLE);
        commentExitImageView.setVisibility(View.VISIBLE);
        commentEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //댓글 쓰기 취소 시 에디트 텍스트, 댓글등록 버튼 비활성화, 키보드 비활성화
    private void KeboardOff(){
        commentEditText.setText("");
        commentEditText.setVisibility(View.GONE);
        commentSubmitButton.setVisibility(View.GONE);
        commentButton.setVisibility(View.VISIBLE);
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void StarsUpdate(boolean isUp){

    }

    private void setProfileImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("users/" + uid + "/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(getApplicationContext())
                            .load(task.getResult())
                            .into(profileImage);
                }
            }
        });
    }

   private void ImageDownload(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        for(int i = 0; i<mainData.imgLength;i++) {
            final ImageView image = new ImageView(getApplicationContext());
            image.setLayoutParams(new LinearLayout.LayoutParams(300,300));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
            params.leftMargin = 8;
            params.rightMargin = 8;
            image.setLayoutParams(params);
            StorageReference imagefileRef = storage.getReference().child("posts/" + boardId + "/image" +i);
            Log.d(TAG, "posts/" + boardId + "/image" +i);
            imagefileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Glide.with(getApplicationContext())
                                .load(task.getResult())
                                .into(image);
                        imageLayout.addView(image);
                    }
                }
            });
        }
    }
}

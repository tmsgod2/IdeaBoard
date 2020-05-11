package com.capstonewansook.ideaboard;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.CommentRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IdeamainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private boolean starCheck;
    private static final String TAG = "IdeamainActivity";
    IdeaMainData mainData;
    String uid;
    String boardId;
    int star;
    int count;
    TextView deleteTextView;
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
    RelativeLayout loadingLayout;

    final ArrayList<CommentRecyclerViewData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ideamain);
        loadingLayout = findViewById(R.id.loadingLayout);
        loadingLayout.bringToFront();

        mainData = (IdeaMainData) getIntent().getSerializableExtra(IdeaMainIn.IDEAMAIN_KEY);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("아이디어 게시판 창");

        deleteTextView = findViewById(R.id.ideamain_delete_textView);
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
        //star = 0; //mainData.stars;
        count = 0;
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.getInstance().collection("users").document(MainActivity.uid).collection(boardId).document("Star").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {
                    if (task.getResult() != null) {
                        starCheck = (boolean) task.getResult().get("star");
                        starCheckBox.setChecked(starCheck);
                    }
                } catch (NullPointerException e) {
                    starCheckBox.setChecked(false);
                }
                if (starCheckBox.isChecked() == false) {
                    starCheckBox.setBackgroundResource(R.drawable.ic_star_black_ranking);
                } else {
                    starCheckBox.setBackgroundResource(R.drawable.ic_star_gold_24dp);
                }
        star = mainData.stars;

        if(uid.equals(MainActivity.uid)){
            deleteTextView.setVisibility(View.VISIBLE);
        }
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsDelete();
            }
        });
        profileImage.setBackground(new ShapeDrawable((new OvalShape())));
        profileImage.setClipToOutline(true);
        starTextView.setText(String.valueOf(mainData.stars));
        starCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starCheckBox.setEnabled(false);
                if(starCheckBox.isChecked()==false) {
                    StarsUpdate(false);
                }
                else{
                    StarsUpdate(true);
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

        commentSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentEditText.getText().toString();
                if(comment.length()>0&&!comment.equals("")){
                    CommentWrite();
                    KeboardOff();
                }
            }
        });

        actionBar.setDisplayHomeAsUpEnabled(true);

        // 댓글 리사이클러뷰 어뎁터 리스트 추가
        CommentRead();

        if (mainData.imgLength > 0)
            ImageDownload();

        setProfileImage();

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

    private void RecyclerViewSet(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    //댓글쓰기 버튼 클릭했을 때 에디트 텍스트랑 댓글 등록 버튼 활성화 밑 키보드 활성화
    private void KeboardOn() {
        commentEditText.setVisibility(View.VISIBLE);
        commentSubmitButton.setVisibility(View.VISIBLE);
        commentExitImageView.setVisibility(View.VISIBLE);
        commentEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //댓글 쓰기 취소 시 에디트 텍스트, 댓글등록 버튼 비활성화, 키보드 비활성화
    private void KeboardOff() {
        commentEditText.setText("");
        commentEditText.setVisibility(View.GONE);
        commentSubmitButton.setVisibility(View.GONE);
        commentButton.setVisibility(View.VISIBLE);
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //추천
    private void StarsUpdate(final boolean up) {
        if(up == true) {
            Map<String, Object> starfield = new HashMap<>();
            starfield.put("star", true);
            db.collection("users")
                .document(MainActivity.uid)
                .collection(boardId)
                .document("Star")
                .set(starfield);
            Map<String, Object> starcollection = new HashMap<>();
            starcollection.put("star", true);
            db.collection("posts")
                    .document(boardId)
                    .collection("star")
                    .document(MainActivity.uid)
                    .set(starcollection)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.collection("posts")
                                    .document(boardId)
                                    .collection("star")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            star = 0;
                                            for (DocumentSnapshot snap : task.getResult()) {
                                                if ((boolean) snap.get("star") == true) {
                                                    star += 1;
                                                }
                                            }
                                            if (starCheckBox.isChecked() == false) {
                                                starCheckBox.setBackgroundResource(R.drawable.ic_star_black_ranking);
                                            } else {
                                                starCheckBox.setBackgroundResource(R.drawable.ic_star_gold_24dp);
                                            }
                                            starTextView.setText(String.valueOf(star));
                                            starCheckBox.setEnabled(true);
                                            Map<String, Object> starplus = new HashMap<>();
                                            starplus.put("stars", star);
                                            db.collection("posts")
                                                    .document(boardId)
                                                    .update(starplus);
                                        }
                                    });
                        }
                    });
        }
        else
        {
            Map<String, Object> starfield = new HashMap<>();
            starfield.put("star", false);
            db.collection("users")
                    .document(MainActivity.uid)
                    .collection(boardId)
                    .document("Star")
                    .set(starfield);
            Map<String, Object> starcollection = new HashMap<>();
            starcollection.put("star", false);
            db.collection("posts")
                    .document(boardId)
                    .collection("star")
                    .document(MainActivity.uid)
                    .set(starcollection)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.collection("posts")
                                    .document(boardId)
                                    .collection("star")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            star = 0;
                                            for (DocumentSnapshot snap : task.getResult()) {
                                                if ((boolean) snap.get("star") == true) {
                                                    star += 1;
                                                }
                                            }
                                            if (starCheckBox.isChecked() == false) {
                                                starCheckBox.setBackgroundResource(R.drawable.ic_star_black_ranking);
                                            } else {
                                                starCheckBox.setBackgroundResource(R.drawable.ic_star_gold_24dp);
                                            }
                                            starTextView.setText(String.valueOf(star));
                                            starCheckBox.setEnabled(true);
                                            Map<String, Object> starplus = new HashMap<>();
                                            starplus.put("stars", star);
                                            db.collection("posts")
                                                    .document(boardId)
                                                    .update(starplus);
                                        }
                                    });
                        }
                    });
        }
    }

    private void setProfileImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("users/" + uid + "/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(getApplicationContext())
                            .load(task.getResult())
                            .into(profileImage);
                }
            }
        });
    }

    private void ImageDownload(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        for (int i = 0; i < mainData.imgLength; i++) {
            final ImageView image = new ImageView(getApplicationContext());
            image.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
            params.leftMargin = 8;
            params.rightMargin = 8;
            image.setLayoutParams(params);
            StorageReference imagefileRef = storage.getReference().child("posts/" + boardId + "/image" + i);
            Log.d(TAG, "posts/" + boardId + "/image" + i);
            imagefileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(getApplicationContext())
                                .load(task.getResult())
                                .into(image);
                        imageLayout.addView(image);
                    }
                }
            });
        }
    }

    private void CommentRead(){
        loadingLayout.setVisibility(View.VISIBLE);
        list.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(boardId).collection("comments")
                .orderBy("date", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    if (task.getResult() != null) {
                        for (QueryDocumentSnapshot snap : task.getResult()) {
                            if (snap.get("name") != null) {
                                list.add(new CommentRecyclerViewData(boardId,snap.getId(),snap.get("uid").toString(),snap.get("name").toString(),((Timestamp)snap.get("date")).toDate(),snap.get("content").toString()));
                            }
                        }
                        if(!list.isEmpty()){
                            RecyclerViewSet((RecyclerView)findViewById(R.id.ideamain_comment_recyclerView), new CommentRecyclerViewAdapter(list));
                        }
                        loadingLayout.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void CommentWrite(){
        loadingLayout.setVisibility(View.VISIBLE);
        Map<String, Object> c = new HashMap<>();
        c.put("uid", MainActivity.uid);
        c.put("name", MainActivity.cus.getName());
        c.put("date", (Object) FieldValue.serverTimestamp());
        c.put("content", commentEditText.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(boardId).collection("comments").add(c)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        CommentRead();
                    }
                });
    }

    private void IsDelete(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteIdea();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();

    }

    private void DeleteIdea(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(boardId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
    }
}

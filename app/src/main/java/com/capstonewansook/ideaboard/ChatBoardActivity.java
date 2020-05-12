package com.capstonewansook.ideaboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatingRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.ChatingRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ChatBoardActivity extends AppCompatActivity {
    private static final String TAG = "ChatBoardActivity";

    String roomId;
    String uid2;
    String name;
    Bitmap profile;
    RecyclerView chatRecyclerView;
    EditText chatEditText;
    Button sendButton;
    ImageView imageInButton;
    ImageView fileInButton;
    ArrayList<ChatingRecyclerViewData> chatingList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatboard);
        roomId = getIntent().getStringExtra("rid");
        uid2 = getIntent().getStringExtra("uid2");
        name = getIntent().getStringExtra("name");

        chatingList = new ArrayList<>();
        chatRecyclerView = findViewById(R.id.chatboard_recyclerview);
        sendButton = findViewById(R.id.chatboard_sendButton);
        chatEditText = findViewById(R.id.chatboard_editText);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = storage.getReference();
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");
            StorageReference profileRef = mStorageRef.child("users/"+uid2+"/profileImage");
            profileRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            profile = BitmapFactory.decodeFile(localFile.getAbsolutePath());


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").document(roomId).collection("chats").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(!task.getResult().isEmpty()){
                                for(QueryDocumentSnapshot snap : task.getResult()){
                                    String message = snap.get("message").toString();
                                    Date date = ((Timestamp)snap.get("date")).toDate();
                                    String fromName = "";
                                    String fromUid = "";
                                    Log.d(TAG, snap.get("fromid").toString());
                                    if(snap.get("fromid").toString().equals(uid2)) {
                                        fromName = name;
                                        fromUid = uid2;
                                    }
                                    else {
                                        fromName = MainActivity.cus.getName();
                                        fromUid = MainActivity.uid;
                                    }

                                    chatingList.add(new ChatingRecyclerViewData(fromName,fromUid, message, date));
                                }
                                RecyclerViewSet(chatRecyclerView,new ChatingRecyclerViewAdapter(chatingList,profile));
                            }
                        }

                    }
                });
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
    private void RecyclerViewSet(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

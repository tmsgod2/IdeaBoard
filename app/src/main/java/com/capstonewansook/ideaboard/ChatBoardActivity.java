package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatingRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.ChatingRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatBoardActivity extends AppCompatActivity {
    private ListView listView;
    private DrawerLayout drawer;
    private ChatlistAdapter chatlistAdapter;
    private static final String TAG = "ChatBoardActivity";
    private final int IMAGE_SEND = 1001;
    private final int TEXT_SEND_TYPE = 0;
    private final int IMAGE_SEND_TYPE = 1;
    private final int FILE_SEND_TYPE = 2;

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
    ChatingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatboard);
        roomId = getIntent().getStringExtra("rid");
        uid2 = getIntent().getStringExtra("uid2");
        name = getIntent().getStringExtra("name");
        String url = getIntent().getStringExtra("prifileUrl");

        chatingList = new ArrayList<>();
        chatRecyclerView = findViewById(R.id.chatboard_recyclerview);
        sendButton = findViewById(R.id.chatboard_sendButton);
        chatEditText = findViewById(R.id.chatboard_editText);
        imageInButton = findViewById(R.id.chatboard_image_button);

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChatReady())
                SendMessage(chatEditText.getText().toString(), TEXT_SEND_TYPE);
            }
        });

        imageInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImage();
            }
        });

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").document(roomId).collection("chats").orderBy("date", Query.Direction.ASCENDING).get()
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
                                    if(snap.get("fromid").toString().equals(uid2)) {
                                        fromName = name;
                                        fromUid = uid2;
                                    }
                                    else if(snap.get("fromid").toString().equals(MainActivity.uid)) {
                                        fromName = MainActivity.cus.getName();
                                        fromUid = MainActivity.uid;
                                    }
                                    else {
                                        fromName = "알 수 없음";
                                        fromUid = "none";
                                    }

                                    ChatingRecyclerViewData chatingdata = new ChatingRecyclerViewData(snap.getId(),fromName,fromUid, message, date);
                                    try{
                                        chatingdata.setType(Integer.parseInt(snap.get("type").toString()));
                                    }
                                    catch (NullPointerException e){
                                        chatingdata.setType(0);
                                    }
                                    chatingdata.setPrifleImage(profile);
                                    chatingList.add(chatingdata);
                                }
                                adapter = new ChatingRecyclerViewAdapter(chatingList,profile,roomId);
                                RecyclerViewSet(chatRecyclerView,adapter);
                                FullDownScroll();
                                ReceiveMessage();
                            }
                            else{
                                adapter = new ChatingRecyclerViewAdapter(chatingList,profile,roomId);
                                RecyclerViewSet(chatRecyclerView,adapter);
                                FullDownScroll();
                                ReceiveMessage();
                            }
                        }

                    }
                });
        actionBar.setTitle(name);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.chatdrawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        chatlistAdapter = new ChatlistAdapter() ;
        chatlistAdapter.addItem(2,"채팅방 서랍") ;
        chatlistAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual_black_24dp),"사진",ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_black_24dp)) ;
        chatlistAdapter.addItem(1,"알림설정") ;
        chatlistAdapter.addItem(null,"채팅방 나가기",ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_black_24dp)) ;
        listView = (ListView) findViewById(R.id.drawer_chat) ;
        listView.setAdapter(chatlistAdapter) ;

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Chatlistitem item = (Chatlistitem) parent.getItemAtPosition(position) ;
                String titleStr = item.getTitleStr();
                if(titleStr == "채팅방 나가기") {
                    FirebaseFirestore.getInstance().collection("chatrooms").document(roomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().get("uid1").equals(MainActivity.uid)) {
                                //FirebaseFirestore.getInstance().collection("chatrooms").document(roomId).update("uid1","none");
                                onBackPressed();
                            }
                            else
                            {
                                //FirebaseFirestore.getInstance().collection("chatrooms").document(roomId).update("uid2","none");
                                onBackPressed();
                            }
                        }
                    });
                }
                else if(titleStr == "사진")
                {
                    Intent intent = new Intent(getApplicationContext(), Chatalbum.class);
                    intent.putExtra("roomid",roomId);
                    intent.putExtra("size",chatingList.size());
                    startActivity(intent);
                }
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_manu,menu);
        return true;
    }

    //상단의 뒤로가기 버튼 클릭시 뒤로 감
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.chatmanbutton:
                if(drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                }
                else
                {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void RecyclerViewSet(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void FullDownScroll(){
        if(!chatingList.isEmpty())
        chatRecyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    private void EditTextDelete(){
        chatEditText.setText("");
    }

    private boolean isChatReady(){
        if(chatEditText.getText().toString().length()>0){
            return true;
        }
        else return false;
    }
    private void SendMessage(String message, int type){
        Map<String, Object> chatData = new HashMap<>();
        chatData.put("fromid", MainActivity.uid);
        chatData.put("date", FieldValue.serverTimestamp());
        chatData.put("message", message);
        chatData.put("type",type);
        EditTextDelete();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").document(roomId).collection("chats").add(chatData)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "채팅메시지 보냄");

            }
        });
    }

    private void ReceiveMessage(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").document(roomId).collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!=null){
                            Log.w(TAG,"실패",e);
                            return;
                        }
                        boolean isSame = true;
                        for(QueryDocumentSnapshot doc:queryDocumentSnapshots){

                            Log.d(TAG,"");
                            String getUid = doc.get("fromid").toString();
                            String getName = "";
                            if(getUid.equals(uid2)){
                                getName = name;
                            }
                            else{
                                getName = MainActivity.cus.getName();
                            }
                            if(chatingList.isEmpty()){
                                isSame = false;
                                try {
                                    chatingList.add(new ChatingRecyclerViewData(doc.getId(), getName, getUid, doc.get("message").toString(), new Date(System.currentTimeMillis()),Integer.parseInt(doc.get("type").toString())));
                                }
                                catch (NullPointerException nullE){
                                    chatingList.add(new ChatingRecyclerViewData(doc.getId(), getName, getUid, doc.get("message").toString(), new Date(System.currentTimeMillis())));
                                }
                            }
                            else if(!chatingList.get(chatingList.size()-1).getChatId().equals(doc.getId())) {
                                isSame = false;
//                                Date date = ((Timestamp)doc.get("date")).toDate();
                                try {
                                    chatingList.add(new ChatingRecyclerViewData(doc.getId(), getName, getUid, doc.get("message").toString(), new Date(System.currentTimeMillis()),Integer.parseInt(doc.get("type").toString())));
                                }
                                catch (NullPointerException nullE){
                                    chatingList.add(new ChatingRecyclerViewData(doc.getId(), getName, getUid, doc.get("message").toString(), new Date(System.currentTimeMillis())));
                                }
                            }

                        }
                        if(!isSame)
                        adapter.notifyItemInserted(chatingList.size()-1);
                        FullDownScroll();
                    }
                });
    }

    //이미지 보내기 클릭시
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_SEND:
                try {
                    Uri image = data.getData();


//                Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                    final StorageReference profileRef = MainActivity.mStorageRef.child("chatings/" + roomId + "/" + chatingList.size());

                    profileRef.putFile(image)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
//
                                    SendMessage(chatingList.size()+"",IMAGE_SEND_TYPE);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
        }
    }

    private void GetImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_SEND);
    }
}

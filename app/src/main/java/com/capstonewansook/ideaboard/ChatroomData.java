package com.capstonewansook.ideaboard;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ChatroomData {
    private String uid;
    ArrayList<ChatRecyclerViewData> chatrooms;
    private ArrayList<String>uids;
    private ArrayList<String>rids;
    private ArrayList<Date> dates;
    private ArrayList<String> messages;
    private ArrayList<String> names;
    private ArrayList<Bitmap> profile;
    FirebaseFirestore db;
    private int check;
    public ChatroomData(final String uid) {
        this.uid = uid;
        chatrooms = new ArrayList<>();
        uids = new ArrayList<>();
        rids = new ArrayList<>();
        dates = new ArrayList<>();
        messages = new ArrayList<>();
        names = new ArrayList<>();
        check = 0;
        db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult() != null) {
                                for (QueryDocumentSnapshot snap: task.getResult()){
                                    if(snap.get("uid1").toString().equals(uid)){
                                        AddData(snap.getId(), snap.get("uid2").toString(),((Timestamp)snap.get("date")).toDate());

                                    }
                                    else if(snap.get("uid2").toString().equals(uid)){
                                        AddData(snap.getId(), snap.get("uid1").toString(),((Timestamp)snap.get("date")).toDate());
                                    }

                                }
                                AddMessages();
                                AddNames();
                            }

                        }

                    }
                });

    }

    public ArrayList<ChatRecyclerViewData> getChatrooms() {

        check=0;

        return chatrooms;
    }

    private void DataUpdate(){
        if(check==2) {
            chatrooms.clear();
            for (int i = 0; i < rids.size(); i++) {
                chatrooms.add(new ChatRecyclerViewData(rids.get(i), uids.get(i), names.get(i), messages.get(i), dates.get(i)));
                Log.d("Chatroom", i + "");
            }
        }
    }

    private void AddMessages(){
        for(int i=0;i<rids.size();i++) {
            final int finalI = i;
            db.collection("chatrooms").document(rids.get(i))
                    .collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    for(QueryDocumentSnapshot snap : task.getResult()){
                                        messages.add(snap.get("message").toString());
                                        Log.d("ChatroomData", "메시지 불러오기 성공"+messages.get(0));
                                    }
                                    check++;
                                    DataUpdate();
                                }
                            }
                        }
                    });
        }
    }
    private void AddNames(){
        for(int i=0;i<rids.size();i++){
            db.collection("users").document(uids.get(i))
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        names.add(task.getResult().get("name").toString());
                        Log.d("ChatroomData", "이름 불러오기 성공"+task.getResult().get("name").toString());
                    }
                    check++;
                    DataUpdate();
                }
            });
        }
    }


    private void AddData(String rid, String uid, Date date){
        rids.add(rid);
        uids.add(uid);
        dates.add(date);

    }

}

package com.capstonewansook.ideaboard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<ChatrData> data;
    FirebaseFirestore db;
    private int check;
    private String roomuid;
    private ViewGroup viewGroup;
    private RecyclerView recyclerView;
    private class ChatrData{
        String rid;
        String uid;
        String name;
        Date date;
        String message;

        public ChatrData(String rid, String uid) {
            this.rid = rid;
            this.uid = uid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    public ChatroomData(final String uid, ViewGroup viewGroup, RecyclerView recyclerView) {
        this.uid = uid;
        data = new ArrayList<>();
        chatrooms = new ArrayList<>();
        check = 0;
        this.viewGroup = viewGroup;
        this.recyclerView = recyclerView;

        db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult() != null) {
                                for (QueryDocumentSnapshot snap: task.getResult()){
                                    if (snap.get("uid1").toString().equals(uid)) {
                                        data.add(new ChatrData(snap.getId(), snap.get("uid2").toString()));

                                    } else if (snap.get("uid2").toString().equals(uid)) {
                                        data.add(new ChatrData(snap.getId(), snap.get("uid1").toString()));
                                    }
                                }
                            AddMessages();
                            AddNames();
                            }

                        }

                    }
                });

    }
    public ChatroomData(final String uid) {
        this.uid = uid;
        data = new ArrayList<>();
        chatrooms = new ArrayList<>();
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
                                        data.add(new ChatrData(snap.getId(), snap.get("uid2").toString()));

                                    }
                                    else if(snap.get("uid2").toString().equals(uid)){
                                        data.add(new ChatrData(snap.getId(), snap.get("uid1").toString()));
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
        if(check==data.size()*2) {
            chatrooms.clear();
            for (int i = 0; i < data.size(); i++) {
                chatrooms.add(new ChatRecyclerViewData(data.get(i).getRid(), data.get(i).getUid(), data.get(i).getName(), data.get(i).getMessage(), data.get(i).getDate()));

            }
        }
    }

    private void AddMessages(){
        for(int i=0;i<data.size();i++) {
            final int finalI = i;
            db.collection("chatrooms").document(data.get(i).getRid())
                    .collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    for (QueryDocumentSnapshot snap : task.getResult()) {
                                        data.get(finalI).setMessage(snap.get("message").toString());
                                        data.get(finalI).setDate(((Timestamp)snap.get("date")).toDate());
                                    }
                                }
                                else{
                                    data.get(finalI).setMessage("");
                                    data.get(finalI).setDate(null);
                                }
                                check++;
                                DataUpdate();
                            }
                        }
                    });
        }
    }
    private void AddNames(){
        for(int i=0;i<data.size();i++){
            final int finalI = i;
            db.collection("users").document(data.get(i).getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        data.get(finalI).setName(task.getResult().get("name").toString());
                        check++;
                        DataUpdate();
                    }
                }
            });
        }
    }


    public void DataAdd(ChatRecyclerViewData data){
        chatrooms.add(data);
    }


    public int RoomIdIndexSearch(String rid){
        for(int i =0; i<chatrooms.size();i++){
            if(chatrooms.get(i).getChatroomID().equals(rid)){
                return i;
            }
        }
        return -1;
    }

    public int Uid2IndexSearch(String uid2){
        for(int i=0;i<chatrooms.size();i++){
            if(chatrooms.get(i).getUid2().equals(uid2)){
                return i;
            }
        }
        return -1;
    }

}

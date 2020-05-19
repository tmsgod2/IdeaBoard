package com.capstonewansook.ideaboard;

import android.content.Context;
import android.content.Intent;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 채팅창 생성을 위한 전역 클래스
public class ChatroomIn {

    //파이어베이스에 채팅창 정보를 입력한다.
    static void ChatroomCreate(final Context context, final String uid2, final String name){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid1",MainActivity.uid);
        hashMap.put("uid2",uid2);
        hashMap.put("date", FieldValue.serverTimestamp());

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chatrooms").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Map<String, Object> chatsMap = new HashMap<>();
                chatsMap.put("create","first");
                MainActivity.chatData.DataAdd(new ChatRecyclerViewData(documentReference.getId(),uid2,name,"",null));
                ChatroomOpen(context,documentReference.getId(),uid2,name);
            }
        });

    }
    static void ChatroomOpen(Context context,String rid,String uid2, String name ){
        Intent intent = new Intent(context, ChatBoardActivity.class);
        intent.putExtra("rid",rid);
        intent.putExtra("uid2",uid2);
        intent.putExtra("name",name);
        intent.putExtra("profileUrl",name);
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    static String isChatroomExist(String uid2){
        ArrayList<ChatRecyclerViewData> list = MainActivity.chatData.getChatrooms();
        for(int i =0 ;i<list.size();i++){
            if(list.get(i).getUid2().equals(uid2)) return list.get(i).getChatroomID();
        }
        return "none";
    }
}

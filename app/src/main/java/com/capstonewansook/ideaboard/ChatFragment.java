package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class ChatFragment extends Fragment {
    FirebaseAuth Ath = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    String TAG = "h";
    ArrayList<ChatRecyclerViewData> list = new ArrayList<>();
    static String message[];
    String uid[];
    String name[];
    ViewGroup rootView;
    String roomuidArray[];
    Date date[];
    int count = 0;
    int g=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =(ViewGroup)inflater.inflate(R.layout.fragment_chat, container, false);
        list = new ArrayList<>();
        message = new String[10];
        uid = new String[10];
        name = new String[10];
        roomuidArray = new String[10];
        date = new Date[10];
        count = 0;
        g=0;



        list.add(new ChatRecyclerViewData("asdasd",R.drawable.iconfinder_32_111002,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
//        list.add(new ChatRecyclerViewData("asdasd",R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));





        //채팅상대의 uid 가져오기,채팅룸 uid 가져오기,Date 가져오기
        db.collection("chatrooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult()!=null){
                                for (DocumentSnapshot snap : task.getResult()) {
                                    Map<String,Object> shot = snap.getData();
                                    if((MainActivity.uid.toString()).equals(shot.get("uid1"))){
                                        uid[count] = String.valueOf(shot.get("uid2"));
                                        roomuidArray[count] = snap.getId().toString();
                                        date[count] = ((Timestamp)shot.get("date")).toDate();
                                        count=count+1;

                                    }
                                    if((MainActivity.uid.toString()).equals(shot.get("uid2"))){
                                        uid[count] = String.valueOf(shot.get("uid1"));
                                        roomuidArray[count] = snap.getId().toString();
                                        date[count] = ((Timestamp)shot.get("date")).toDate();
                                        count=count+1;
                                    }


//                                    uid = String.valueOf(shot.get("uid2"));
//                                    String message = String.valueOf(shot.get("message"));
//                                    Toast.makeText(getActivity(),"룸어레이"+roomuidArray[0], Toast.LENGTH_SHORT).show();
//                                    String date = String.valueOf(shot.get("date"));
//                                    ChatRecyclerViewData Crd = new ChatRecyclerViewData("123",R.drawable.iconfinder_32_111002,name,message,new Date(System.currentTimeMillis()));
//                                    list.add(Crd);
//                                    RecyclerViewSet(rootView,list,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),new ChatRecyclerViewAdapter(list),R.id.chat_re_name_textView);
                                }

                            }

                            //메세지 가져오기
                            for(;g<count;g++){
                                db2.collection("chatrooms").document(roomuidArray[g]).collection("chats")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if(task.getResult()!=null){
                                                        for (DocumentSnapshot snap : task.getResult()) {
                                                            Map<String,Object> shot = snap.getData();
                                                            message[g] = String.valueOf(shot.get("message"));
//                                    Date date = shot.get("date");
//                                                            Toast.makeText(getActivity(),"메세지"+message[g], Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                } else {
                                                    Log.w(TAG, "Error getting documents.", task.getException());
                                                }
                                            }
                                        });
                            }


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });



           //채팅주인의 이름 가져오기
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult()!=null){
                                for(int i = 0;i < count; i++) {
                                    for (DocumentSnapshot snap : task.getResult()) {
                                        Map<String, Object> shot = snap.getData();
                                        if ((snap.getId().toString()).equals(uid[i])) {
                                            name[i] = String.valueOf(shot.get("name"));
//                                            Toast.makeText(getActivity(),"이름"+ name[i], Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                }
                                for(int i = 0 ;i < count; i++)
                                {
                                    ChatRecyclerViewData Crd = new ChatRecyclerViewData("123",R.drawable.iconfinder_32_111002,name[i],message[i],date[i]);
                                    Toast.makeText(getActivity(),Crd.getMessage(), Toast.LENGTH_SHORT).show();
                                    list.add(Crd);
                                }
//                                Toast.makeText(getActivity(),"리사뷰1111111", Toast.LENGTH_SHORT).show();
                                RecyclerViewSet(rootView,list,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),new ChatRecyclerViewAdapter(list),R.id.chat_re_name_textView);

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        return  rootView;



//        RecyclerViewSet(rootView,list,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),new ChatRecyclerViewAdapter(list),R.id.chat_re_name_textView);
//        return  rootView;
    }



    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import static com.capstonewansook.ideaboard.MainActivity.chatroomData;


public class ChatFragment extends Fragment {
    FirebaseAuth Ath = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    String TAG = "h";
    ArrayList<ChatRecyclerViewData> list = new ArrayList<>();
    String message[];
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


        RecyclerViewSet(rootView,chatroomData.getChatrooms(),(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),new ChatRecyclerViewAdapter(chatroomData.getChatrooms()),R.id.chat_re_name_textView);

        return  rootView;
    }



    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

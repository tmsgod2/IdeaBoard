package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    String TAG = "h";
    ViewGroup rootView;
    ChatroomData chatroomData=new ChatroomData(MainActivity.uid);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =(ViewGroup)inflater.inflate(R.layout.fragment_chat, container, false);

        return  rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerViewSet(rootView,chatroomData.getChatrooms(),(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),
                new ChatRecyclerViewAdapter(chatroomData.getChatrooms()),R.id.chat_re_name_textView);
    }

    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

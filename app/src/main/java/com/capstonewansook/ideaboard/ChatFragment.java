package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;

import static com.capstonewansook.ideaboard.MainActivity.chatData;


public class ChatFragment extends Fragment {
    String TAG = "h";
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =(ViewGroup)inflater.inflate(R.layout.fragment_chat, container, false);
        return  rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerViewSet(rootView,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),
                new ChatRecyclerViewAdapter(chatData.getChatrooms()));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void RecyclerViewSet(final ViewGroup view, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

}

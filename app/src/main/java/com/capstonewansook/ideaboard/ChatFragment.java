package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;

import static com.capstonewansook.ideaboard.MainActivity.chatData;


public class ChatFragment extends Fragment implements MyFragmentRefreshCallBack {


    private static final String TAG = "ChatFragment";
    static ViewGroup rootView;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SetChatRecycler();
        }
    };
    public static MyFragmentRefreshCallBack myFragmentRefreshCallBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =(ViewGroup)inflater.inflate(R.layout.fragment_chat, container, false);
        myFragmentRefreshCallBack = this;
        return  rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SetChatRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void SetChatRecycler(){
        RecyclerViewSet(rootView,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),
                new ChatRecyclerViewAdapter(chatData.getChatrooms()));

    }
    private void RecyclerViewSet(final ViewGroup view, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void myFragmentRefresh() {
        handler.sendEmptyMessage(0);

    }
}

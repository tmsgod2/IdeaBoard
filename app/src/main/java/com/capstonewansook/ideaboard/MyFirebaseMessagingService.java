package com.capstonewansook.ideaboard;

import android.util.Log;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    private ChatFragment chatFragment;
    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification() != null){
            Map<String, String> mes = remoteMessage.getData();
            Log.d(TAG, remoteMessage.getData().toString());
            int change = MainActivity.chatData.Uid2IndexSearch(mes.get("uid2"));
            if(change>=0) {
                MainActivity.chatData.getChatrooms().get(change).setMessage(mes.get("message"));
                MainActivity.chatData.getChatrooms().get(change).setDate(new Date(System.currentTimeMillis()));
            }
            else{
                MainActivity.chatData.DataAdd(new ChatRecyclerViewData(mes.get("rid"),mes.get("uid2"),mes.get("name"),mes.get("message"),new Date(System.currentTimeMillis())));
            }

            if(MainActivity.state == 1){
                ChatFragment.myFragmentRefreshCallBack.myFragmentRefresh();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

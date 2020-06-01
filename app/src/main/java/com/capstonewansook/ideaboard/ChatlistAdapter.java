package com.capstonewansook.ideaboard;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatlistAdapter extends BaseAdapter {
    private ArrayList<Chatlistitem> chatViewItemList = new ArrayList<Chatlistitem>() ;
    private String roomId;
    private boolean noAlram;
    public ChatlistAdapter(){

    }

    @Override
    public int getCount() {
        return chatViewItemList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return chatViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatlist_item, parent, false);
        }
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.chatlist_image) ;
        TextView TextView = (TextView) convertView.findViewById(R.id.chatlist_textview1) ;
        ImageView iconImageView2 = (ImageView) convertView.findViewById(R.id.chatlist_image2) ;
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chatlistcheckbox) ;

        Chatlistitem chatlistitem = chatViewItemList.get(position);

        iconImageView.setImageDrawable(chatlistitem.getIconDrawable());
        TextView.setText(chatlistitem.getTitleStr());
        iconImageView2.setImageDrawable(chatlistitem.getIconDrawable2());
        if(chatlistitem.isCheck() == 1) {
            checkBox.setChecked(noAlram);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("chatrooms").document(roomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                HashMap<String, Object> map = new HashMap<>();
                                noAlram = !noAlram;
                                if(task.getResult().get("uid1").toString().equals(MainActivity.uid)){
                                    map.put("uid1NoSign",noAlram);
                                }
                                else{
                                    map.put("uid2NoSign",noAlram);
                                }
                                db.collection("chatrooms").document(roomId).update(map);
                            }
                        }
                    });
                }
            });
        }
        if(chatlistitem.isCheck() == 2){
            TextView.setTypeface(null, Typeface.BOLD);
            TextView.setPadding(0,0,0,0);

            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        else
        {
            TextView.setTypeface(null, Typeface.NORMAL);
            TextView.setPadding(25,0,0,0);
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }

        return convertView;
    }
    public void addItem(Drawable icon, String title,Drawable icon2) {
        Chatlistitem item = new Chatlistitem();
        item.setCheck(0);
        item.setIconDrawable(icon);
        item.setIconDrawable2(icon2);
        item.setTitleStr(title);
        chatViewItemList.add(item);
    }
    public void addItem(int i,String title) {
        Chatlistitem item = new Chatlistitem();
        item.setTitleStr(title);
        item.setCheck(i);
        chatViewItemList.add(item);
    }
    public void addItem(int i , String title, String roomId, boolean noAlram){
        Chatlistitem item = new Chatlistitem();
        item.setTitleStr(title);
        item.setCheck(i);
        chatViewItemList.add(item);
        this.roomId = roomId;
        this.noAlram = noAlram;
    }

}

package com.capstonewansook.ideaboard;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatlistAdapter extends BaseAdapter {
    private ArrayList<Chatlistitem> chatViewItemList = new ArrayList<Chatlistitem>() ;
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
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chatlistcheckbox) ;

        Chatlistitem chatlistitem = chatViewItemList.get(position);

        iconImageView.setImageDrawable(chatlistitem.getIconDrawable());
        TextView.setText(chatlistitem.getTitleStr());
        iconImageView2.setImageDrawable(chatlistitem.getIconDrawable2());
        if(chatlistitem.isCheck() == 1) {
            checkBox.setVisibility(View.VISIBLE);
        }
        if(chatlistitem.isCheck() == 2){
            TextView.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        }
        else
        {
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
    }public void addItem(int i,String title) {
        Chatlistitem item = new Chatlistitem();
        item.setTitleStr(title);
        item.setCheck(i);
        chatViewItemList.add(item);
    }
}

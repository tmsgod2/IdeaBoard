package com.capstonewansook.ideaboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ChatalbumAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ImageView> iv;
    private int count;
    public ChatalbumAdapter(Context context,ArrayList<ImageView> iv){
        this.context = context;
        this.iv = iv;
        count = iv.size();

    }
    @Override
    public int getCount() {
        return iv.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        imageView = iv.get(position);

        return imageView;
    }
}

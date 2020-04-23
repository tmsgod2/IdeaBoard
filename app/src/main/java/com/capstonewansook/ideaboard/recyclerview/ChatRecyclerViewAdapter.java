package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.ChatBoardActivity;
import com.capstonewansook.ideaboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {
    //의미없는 주석
    private ArrayList<ChatRecyclerViewData> mData;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nameText,messageText,dateText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.chat_re_profile_imageView);
            nameText = itemView.findViewById(R.id.chat_re_name_textView);
            messageText = itemView.findViewById(R.id.chat_re_message_textView);
            dateText = itemView.findViewById(R.id.chat_re_date_textView);
            profileImage.setBackground(new ShapeDrawable((new OvalShape())));
            profileImage.setClipToOutline(true);
        }
    }

    public ChatRecyclerViewAdapter(ArrayList<ChatRecyclerViewData> mData) {
        this.mData = mData;
        Collections.sort(mData);
    }

    @NonNull
    @Override
    public ChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_recyclerview,parent,false);
        ChatRecyclerViewAdapter.ViewHolder vh = new ChatRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerViewAdapter.ViewHolder holder, final int position) {
        String name = mData.get(position).getName();
        int profile = mData.get(position).getProfileImage();
        String message = mData.get(position).getMessage();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        holder.profileImage.setImageResource(profile);
        holder.nameText.setText(name);
        holder.messageText.setText(message);
        holder.dateText.setText(format.format(date));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ChatBoardActivity.class);
                Toast.makeText(context,mData.get(position).getName()+" " + mData.get(position).getDate(),Toast.LENGTH_SHORT).show();
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

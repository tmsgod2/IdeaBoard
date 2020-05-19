package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.ChatBoardActivity;
import com.capstonewansook.ideaboard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {
    //의미없는 주석
    private ArrayList<ChatRecyclerViewData> mData;
    private FirebaseStorage storage;
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
        storage = FirebaseStorage.getInstance();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_recyclerview,parent,false);
        ChatRecyclerViewAdapter.ViewHolder vh = new ChatRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatRecyclerViewAdapter.ViewHolder holder, final int position) {
        String name = mData.get(position).getName();
        String message = mData.get(position).getMessage();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


//        holder.profileImage.setImageResource(profile);
        holder.nameText.setText(name);
        holder.messageText.setText(message);
        try {
            holder.dateText.setText(format.format(date));
        }
        catch (NullPointerException e){
            holder.dateText.setText("");
        }

        Log.d("ChatRecyclerViewAdapter",mData.get(position).getUid2());
        StorageReference profileRef = storage.getReference().child("users/"+mData.get(position).getUid2()+"/profileImage.jpg");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(holder.profileImage.getContext())
                                .load(task.getResult())
                                .into(holder.profileImage);
                        mData.get(position).setProfile(task.getResult().toString());
                    } else {
                        holder.profileImage.setImageResource(R.drawable.kakaotalklog2);
                    }
                }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(),mData.get(position).getChatroomID()+" " + mData.get(position).getDate(),Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent intent = new Intent(context, ChatBoardActivity.class);
                intent.putExtra("rid",mData.get(position).getChatroomID());
                intent.putExtra("uid2",mData.get(position).getUid2());
                intent.putExtra("name",mData.get(position).getName());
                intent.putExtra("profileUrl",mData.get(position).getProfile());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void move(int postion){
        notifyItemMoved(postion, 0);
    }
}

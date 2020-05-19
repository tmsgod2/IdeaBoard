package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.MainActivity;
import com.capstonewansook.ideaboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatingRecyclerViewAdapter extends RecyclerView.Adapter<ChatingRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatingRecyclerViewData> mData;
    private Bitmap profile;
    private String roomId;

    public ChatingRecyclerViewAdapter(ArrayList<ChatingRecyclerViewData> mData,Bitmap profile,String roomId) {
        this.mData = mData;
        this.profile = profile;
        this.roomId = roomId;
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getUid().equals(MainActivity.uid)){
            return 1;
        }else
            return 2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_bubble_you_recyclerview,parent,false);
        if(viewType == 1){
            view = inflater.inflate(R.layout.chat_bubble_me_recyclerview,parent,false);
        }
        ChatingRecyclerViewAdapter.ViewHolder vh = new ChatingRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String name = mData.get(position).getName();
        String message = mData.get(position).getMessage();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        holder.nameText.setText(name);

        holder.dateText.setText(format.format(date));
        holder.profileImage.setImageBitmap(profile);
        holder.dateMeText.setText(format.format(date));
        if(mData.get(position).getType() == 1){
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference imageRef = storage.getReference().child("chatings/" + roomId + "/" + mData.get(position).message);
//            imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Glide.with(holder.itemView.getContext())
//                                .load(task.getResult())
//
//                    }
//                }
//            })
        }
        holder.messageText.setText(message);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout con;
        ImageView profileImage;
        TextView nameText;
        TextView messageText;
        TextView dateText;
        TextView dateMeText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            con = itemView.findViewById(R.id.chat_bubble_re_layout);
            profileImage = itemView.findViewById(R.id.chat_bubble_re_profile_imageView);
            nameText = itemView.findViewById(R.id.chat_bubble_re_name_textView);
            messageText = itemView.findViewById(R.id.chat_bubble_re_message_textView);
            dateText = itemView.findViewById(R.id.chat_bubble_re_date_textView);
            dateMeText = itemView.findViewById(R.id.chat_bubble_re_me_date_textView);
            profileImage.setBackground(new ShapeDrawable((new OvalShape())));
            profileImage.setClipToOutline(true);
        }
    }
}

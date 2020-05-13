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
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.MainActivity;
import com.capstonewansook.ideaboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatingRecyclerViewAdapter extends RecyclerView.Adapter<ChatingRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatingRecyclerViewData> mData;
    private Bitmap profile;

    public ChatingRecyclerViewAdapter(ArrayList<ChatingRecyclerViewData> mData,Bitmap profile) {
        this.mData = mData;
        this.profile = profile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_bubble_me_recyclerview,parent,false);
        ChatingRecyclerViewAdapter.ViewHolder vh = new ChatingRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String name = mData.get(position).getName();
        String message = mData.get(position).getMessage();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        holder.nameText.setText(name);
        holder.messageText.setText(message);
        holder.dateText.setText(format.format(date));
        holder.profileImage.setImageBitmap(profile);

        if(mData.get(position).getUid().equals(MainActivity.uid)){
            holder.nameText.setVisibility(View.INVISIBLE);
            holder.profileImage.setVisibility(View.INVISIBLE);
            holder.dateText.setVisibility(View.GONE);
            holder.dateMeText.setVisibility(View.VISIBLE);
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout constraintLayout =
        }

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
            con = itemView.
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

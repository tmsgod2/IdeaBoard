package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CommentRecyclerViewData> mData;

    public CommentRecyclerViewAdapter(ArrayList<CommentRecyclerViewData> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.comment_recyclerview,parent,false);
        CommentRecyclerViewAdapter.ViewHolder vh = new CommentRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String name = mData.get(position).getName();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        holder.nameTextView.setText(name);
        holder.dateTextView.setText(format.format(date));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), mData.get(position).getUid(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView dateTextView;
        TextView contentTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.comment_name_textView);
            dateTextView = itemView.findViewById(R.id.comment_date_textView);
            contentTextView = itemView.findViewById(R.id.comment_content_textView);
        }
    }
}

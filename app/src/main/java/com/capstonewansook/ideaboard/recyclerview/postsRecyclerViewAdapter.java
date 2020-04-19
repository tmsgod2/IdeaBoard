package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.IdeamainActivity;
import com.capstonewansook.ideaboard.R;

import java.util.ArrayList;

public class postsRecyclerViewAdapter extends RecyclerView.Adapter<postsRecyclerViewAdapter.ViewHolder>
{
    ArrayList<postsRecyclerViewData> arrayList = null;


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView1,imageView2;
        TextView textView1,textView2,textView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.posts_imageView); // 글 첫번째 사진
            imageView2 = itemView.findViewById(R.id.posts_imageView2); // 별모양
            textView1 = itemView.findViewById(R.id.posts_textView2); // YY,MM,DD
            textView2 = itemView.findViewById(R.id.posts_textView1); // 글제목
            textView3 = itemView.findViewById(R.id.posts_textView3); // 추천수

        }
    }
    public postsRecyclerViewAdapter(ArrayList<postsRecyclerViewData> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public postsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.posts_recyclerview,parent,false);
        postsRecyclerViewAdapter.ViewHolder vh = new postsRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull postsRecyclerViewAdapter.ViewHolder holder, int position) {
        int image = arrayList.get(position).getImage();
        int image2 = arrayList.get(position).getImage2();
        String name = arrayList.get(position).getName();
        String yymmdd = arrayList.get(position).getYymmdd();
        int star = arrayList.get(position).getStar();
        holder.imageView1.setImageResource(image);
        holder.imageView2.setImageResource(image2);
        holder.textView1.setText(yymmdd);
        holder.textView2.setText(name);
        holder.textView3.setText(String.valueOf(star));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), IdeamainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

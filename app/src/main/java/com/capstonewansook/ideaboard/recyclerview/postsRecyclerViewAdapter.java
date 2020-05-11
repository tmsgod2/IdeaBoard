package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.IdeaMainIn;
import com.capstonewansook.ideaboard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class postsRecyclerViewAdapter extends RecyclerView.Adapter<postsRecyclerViewAdapter.ViewHolder>
{
    ArrayList<postsRecyclerViewData> arrayList = null;
    private FirebaseStorage storage;


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView1,imageView2;
        TextView textView1,textView2,textView3,textView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.posts_imageView); // 글 첫번째 사진
            imageView2 = itemView.findViewById(R.id.posts_imageView2); // 별모양
            textView1 = itemView.findViewById(R.id.posts_textView1); // YY,MM,DD
            textView2 = itemView.findViewById(R.id.posts_textView2); // 글제목
            textView3 = itemView.findViewById(R.id.posts_textView3); // 추천수
            textView4 = itemView.findViewById(R.id.posts_textView4); // 추천수

        }
    }
    public postsRecyclerViewAdapter(ArrayList<postsRecyclerViewData> arrayList) {
        storage = FirebaseStorage.getInstance();
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
    public void onBindViewHolder(@NonNull final postsRecyclerViewAdapter.ViewHolder holder, final int position) {
        int image2 = arrayList.get(position).getImage2();
        String name = arrayList.get(position).getName();
        String yymmdd = arrayList.get(position).getYymmdd();
        String content = arrayList.get(position).getContent();
        int star = arrayList.get(position).getStar();

            StorageReference profileRef = storage.getReference().child("posts/" + arrayList.get(position).getBoardId()+"/image0");
            profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(holder.imageView1.getContext())
                                .load(task.getResult())
                                .into(holder.imageView1);
                    } else {
                        holder.imageView1.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                    }
                }
            });



        holder.imageView2.setImageResource(image2);
        holder.textView1.setText(yymmdd);
        holder.textView2.setText(name);
        holder.textView3.setText(String.valueOf(star));
        holder.textView4.setText(content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdeaMainIn.IdeaMainConnect(holder.itemView.getContext(), arrayList.get(position).getBoardId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

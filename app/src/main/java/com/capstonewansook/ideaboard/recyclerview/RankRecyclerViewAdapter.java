package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.ViewHolder> {
    private ArrayList<RankRecyclerViewData> mData = null;
    private FirebaseStorage storage;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView nameText,ofiiceText,starText,manyStarText;

        public ViewHolder(View itemView){
            super(itemView);

            profileImage = itemView.findViewById(R.id.rank_re_profile_imageView);
            nameText = itemView.findViewById(R.id.rank_re_name_textView);
            ofiiceText = itemView.findViewById(R.id.rank_re_office_textView);
            starText = itemView.findViewById(R.id.rank_re_star_textView);
            manyStarText = itemView.findViewById(R.id.rank_re_manyStar_textView);
            profileImage.setBackground(new ShapeDrawable((new OvalShape())));
            profileImage.setClipToOutline(true);

        }

    }

    public RankRecyclerViewAdapter(ArrayList<RankRecyclerViewData> list){
        storage = FirebaseStorage.getInstance();
        mData = list;
        Collections.sort(mData);
        for(int i = 0; i<mData.size();i++){
            mData.get(i).setGrade(i+1);
        }
    }

    @NonNull
    @Override
    public RankRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ranking_recyclerview, parent, false);
        RankRecyclerViewAdapter.ViewHolder vh = new RankRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RankRecyclerViewAdapter.ViewHolder holder, final int position) {
        String name = mData.get(position).getName();
        String office = mData.get(position).getOffice();
        int manyStar = mData.get(position).getStar();
        int grade = mData.get(position).getGrade();


        StorageReference profileRef = storage.getReference().child("users/"+mData.get(position).getUid()+"/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(holder.profileImage.getContext())
                            .load(task.getResult())
                            .into(holder.profileImage);
                } else {
                    holder.profileImage.setImageResource(R.drawable.kakaotalklog2);
                }
            }
        });
        holder.nameText.setText(name);
        holder.ofiiceText.setText(office);
        holder.manyStarText.setText(String.valueOf(manyStar));
        //이거 바꿔야 할 수도 있음
        holder.starText.setText(String.valueOf(grade));

        switch (grade){
            case 1:
                holder.starText.setBackgroundResource(R.drawable.ic_star_gold_24dp);
                break;
            case 2:
                holder.starText.setBackgroundResource(R.drawable.ic_star_silver_24dp);
                break;
            case 3:
                holder.starText.setBackgroundResource(R.drawable.ic_star_bronze_24dp);
                holder.starText.setTextColor(Color.WHITE);
                break;
            default:
                holder.starText.setBackgroundResource(R.drawable.ic_star_black_ranking);
                holder.starText.setTextColor(Color.WHITE);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),mData.get(position).getUid(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}

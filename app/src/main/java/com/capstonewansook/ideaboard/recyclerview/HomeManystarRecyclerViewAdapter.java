package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.IdeaMainIn;
import com.capstonewansook.ideaboard.R;

import java.util.ArrayList;
import java.util.Collections;

public class HomeManystarRecyclerViewAdapter extends RecyclerView.Adapter<HomeManystarRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HomeManystarRecyclerViewData> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,manystar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.home_re_manystar_title_textview);
            manystar = itemView.findViewById(R.id.home_re_manystar_num_textView);
        }
    }

    public HomeManystarRecyclerViewAdapter(ArrayList<HomeManystarRecyclerViewData> mData) {
        this.mData = mData;
        Collections.sort(mData);
    }

    @NonNull
    @Override
    public HomeManystarRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_manystar_recyclerview,parent,false);
        HomeManystarRecyclerViewAdapter.ViewHolder vh = new HomeManystarRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeManystarRecyclerViewAdapter.ViewHolder holder, final int position) {

        String title = mData.get(position).getTitle();
        int stars = mData.get(position).getStars();

        holder.title.setText(title);
        holder.manystar.setText(String.valueOf(stars));

        //리사이클러뷰 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdeaMainIn.IdeaMainConnect(holder.itemView.getContext(), mData.get(position).getBoardID());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HomeNewideaRecyclerViewAdapter extends RecyclerView.Adapter<HomeNewideaRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HomeNewideaRecyclerViewData> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.home_re_newidea_title_textView);
            date = itemView.findViewById(R.id.home_re_newidea_date_textView);
        }
    }

    public HomeNewideaRecyclerViewAdapter(ArrayList<HomeNewideaRecyclerViewData> mData) {
        this.mData = mData;
        Collections.sort(mData);
    }

    @NonNull
    @Override
    public HomeNewideaRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_newidea_recyclerview,parent,false);
        HomeNewideaRecyclerViewAdapter.ViewHolder vh = new HomeNewideaRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewideaRecyclerViewAdapter.ViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        holder.title.setText(title);
        holder.date.setText(format.format(date));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

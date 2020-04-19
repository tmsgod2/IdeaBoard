package com.capstonewansook.ideaboard.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.IdeamainActivity;
import com.capstonewansook.ideaboard.R;

import java.util.ArrayList;

public class IdeaRecyclerViewAdapter extends RecyclerView.Adapter< IdeaRecyclerViewAdapter.CustromViewHolder> {
    private ArrayList<IdeaRecycletViewData> arrayList;

    public IdeaRecyclerViewAdapter(ArrayList<IdeaRecycletViewData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public IdeaRecyclerViewAdapter.CustromViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_recyclerview,parent,false);
        CustromViewHolder holder = new CustromViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IdeaRecyclerViewAdapter.CustromViewHolder holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profill());
        holder.iv_recomand.setImageResource(arrayList.get(position).getIv_recomand());
        holder.iv_write.setImageResource(arrayList.get(position).getIv_write());
        holder.tv_autor.setText(arrayList.get(position).getTv_autor());
        holder.tv_day.setText(arrayList.get(position).getTv_day());
        holder.tv_recomand.setText(arrayList.get(position).getTv_recomand());
        holder.tv_write.setText(arrayList.get(position).getTv_write());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_day.getText().toString();
                Toast.makeText(v.getContext(),curName,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), IdeamainActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null!=arrayList?arrayList.size():0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);//새로고침
        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustromViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_profile;
        protected TextView tv_autor;
        protected  TextView tv_day;
        protected ImageView iv_recomand;
        protected ImageView iv_write;
        protected TextView tv_recomand;
        protected TextView tv_write;

        public CustromViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile=(ImageView) itemView.findViewById(R.id.iv_profile);
            this.iv_recomand=(ImageView) itemView.findViewById(R.id.iv_recommand);
            this.iv_write=(ImageView) itemView.findViewById(R.id.iv_write);
            this.tv_autor=(TextView) itemView.findViewById(R.id.tv_author);
            this.tv_day=(TextView) itemView.findViewById(R.id.tv_day);
            this.tv_recomand=(TextView) itemView.findViewById(R.id.tv_recomand);
            this.tv_write=(TextView) itemView.findViewById(R.id.tv_write);

        }
    }
}

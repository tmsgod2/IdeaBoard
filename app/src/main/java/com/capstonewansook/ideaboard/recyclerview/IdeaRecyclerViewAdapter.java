package com.capstonewansook.ideaboard.recyclerview;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdeaRecyclerViewAdapter extends RecyclerView.Adapter< IdeaRecyclerViewAdapter.CustromViewHolder> implements Filterable {
    private ArrayList<IdeaRecycletViewData> arrayList;
    private ArrayList<IdeaRecycletViewData> arrayListall;
    private FirebaseStorage storage;

    public IdeaRecyclerViewAdapter(ArrayList<IdeaRecycletViewData> arrayList) {
        storage = FirebaseStorage.getInstance();
        this.arrayList = arrayList;
        this.arrayListall = new ArrayList<>(arrayList);

    }

    @NonNull
    @Override
    public IdeaRecyclerViewAdapter.CustromViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_recyclerview,parent,false);
        CustromViewHolder holder = new CustromViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IdeaRecyclerViewAdapter.CustromViewHolder holder, final int position) {///
        Date date = arrayList.get(position).getTv_day();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



        StorageReference profileRef = storage.getReference().child("users/"+arrayList.get(position).getIv_profill()+"/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(holder.iv_profile.getContext())
                            .load(task.getResult())
                            .into(holder.iv_profile);
                } else {
                    holder.iv_profile.setImageResource(R.drawable.kakaotalklog2);
                }
            }
        });


        StorageReference profileRef2 = storage.getReference().child("posts/"+arrayList.get(position).getIdpost()+"/image0");
        profileRef2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(holder.idpost.getContext())
                            .load(task.getResult())
                            .into(holder.idpost);
                } else {
//                    holder.idpost.setImageResource(R.drawable.kakaotalklog2);
                }
            }
        });
//        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profill());
//        holder.iv_recomand.setImageResource(arrayList.get(position).getIv_recomand());
//        holder.iv_write.setImageResource(arrayList.get(position).getIv_write());
        holder.tv_story.setText(arrayList.get(position).getTv_story());
        holder.tv_day.setText(format.format(date));
        holder.tv_recomand.setText(arrayList.get(position).getTv_recomand());
//        holder.tv_write.setText(arrayList.get(position).getTv_write());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String curName = holder.tv_day.getText().toString();
//                Intent intent = new Intent(v.getContext(), IdeamainActivity.class);
//                v.getContext().startActivity(intent);
                IdeaMainIn.IdeaMainConnect(holder.itemView.getContext(), arrayList.get(position).getIdpost());
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


/////
    public void dataSetChanged(ArrayList<IdeaRecycletViewData> exampleList) {
        arrayList = exampleList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<IdeaRecycletViewData> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListall);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (IdeaRecycletViewData item : arrayListall) {
                    //TODO filter 대상 setting
                    if (item.getTv_story().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
/////



    public class CustromViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_profile;
        protected TextView tv_story;
        protected  TextView tv_day;
        protected ImageView iv_recomand;
        protected ImageView iv_write;
        protected TextView tv_recomand;
        protected TextView tv_write;
        protected ImageView idpost;

        public CustromViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile=(ImageView) itemView.findViewById(R.id.iv_profile);
            this.iv_recomand=(ImageView) itemView.findViewById(R.id.iv_recommand);
            this.iv_write=(ImageView) itemView.findViewById(R.id.iv_write);
            this.tv_story=(TextView) itemView.findViewById(R.id.tv_story);
            this.tv_day=(TextView) itemView.findViewById(R.id.tv_day);
            this.tv_recomand=(TextView) itemView.findViewById(R.id.tv_recomand);
            this.tv_write=(TextView) itemView.findViewById(R.id.tv_write);
            this.idpost=(ImageView) itemView.findViewById(R.id.idpost);
            iv_profile.setBackground(new ShapeDrawable((new OvalShape())));
            iv_profile.setClipToOutline(true);

        }
    }

/////
    public interface onItemListener {
        void onItemClicked(int position);
        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
    }/////
}

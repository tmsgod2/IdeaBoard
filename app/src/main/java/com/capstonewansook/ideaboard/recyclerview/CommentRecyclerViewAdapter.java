package com.capstonewansook.ideaboard.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.MainActivity;
import com.capstonewansook.ideaboard.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String content = mData.get(position).getContent();


        holder.nameTextView.setText(name);
        holder.dateTextView.setText(format.format(date));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), mData.get(position).getUid() + mData.get(position).getDate(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(mData.get(position).getUid().equals(MainActivity.uid)){
                    IsDelete(holder.itemView.getContext(),mData.get(position).getBoardId(), mData.get(position).getCommentId(),position);
                }
                return true;
            }
        });
        holder.contentTextView.setText(content);



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

    private void CommentDelete(final Context context, String boardId, String commentId , final int position){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(boardId).collection("comments").document(commentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        remove(position);
                        Toast.makeText(context, "삭제완료",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void IsUpdate(final Context context, final String boardId, final String commentId, final int position){}
    private void IsDelete(final Context context, final String boardId, final String commentId, final int position){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);
        alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CommentDelete(context,boardId,commentId,position);
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();

    }

    public void remove(int position){
        try{
            mData.remove(position);
            notifyItemRemoved(position);//새로고침
        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
}

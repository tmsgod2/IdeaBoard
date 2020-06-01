package com.capstonewansook.ideaboard.recyclerview;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstonewansook.ideaboard.Chatalbumclick;
import com.capstonewansook.ideaboard.MainActivity;
import com.capstonewansook.ideaboard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatingRecyclerViewAdapter extends RecyclerView.Adapter<ChatingRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatingRecyclerViewData> mData;
    private Bitmap profile;
    private String roomId;

    public ChatingRecyclerViewAdapter(ArrayList<ChatingRecyclerViewData> mData,Bitmap profile,String roomId) {
        this.mData = mData;
        this.profile = profile;
        this.roomId = roomId;
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getUid().equals(MainActivity.uid)){
            if(mData.get(position).getType()==1){
                return 3;
            }
            if(mData.get(position).getType() == 2) return 5;
            return 1;
        }else {
            if(mData.get(position).getType()==1){
                return 4;
            }
            if(mData.get(position).getType() == 2) return 6;
            return 2;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_bubble_you_recyclerview,parent,false);
        if(viewType == 1){
            view = inflater.inflate(R.layout.chat_bubble_me_recyclerview,parent,false);
        }
        if(viewType == 3){
            view = inflater.inflate(R.layout.chat_bubble_me_image_recyclerview,parent,false);
        }
        if(viewType == 4) {
            view = inflater.inflate(R.layout.chat_bubble_you_image_recyclerview,parent,false);
        }
        if(viewType == 5){
            view = inflater.inflate(R.layout.chat_bubble_me_file_recyclerview,parent,false);
        }
        if(viewType == 6) {
            view = inflater.inflate(R.layout.chat_bubble_you_file_recyclerview,parent,false);
        }

        ChatingRecyclerViewAdapter.ViewHolder vh = new ChatingRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String name = mData.get(position).getName();
        final String message = mData.get(position).getMessage();
        Date date = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        holder.nameText.setText(name);
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"+mData.get(position).getUid()+"/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(holder.itemView.getContext())
                            .load(task.getResult())
                            .into(holder.profileImage);
                }
            }
        });
        holder.dateText.setText(format.format(date));
        holder.dateMeText.setText(format.format(date));
        if(mData.get(position).getType() == 1){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference imageRef = storage.getReference().child("chatings/" + roomId + "/" + mData.get(position).message);
            imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Glide.with(holder.itemView.getContext())
                                .load(task.getResult())
                                .into(holder.chatImageView);
                    }
                }
            });
        }
        holder.messageText.setText(message);
        if(mData.get(position).getType()==2){
            holder.messageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference fileRef = storage.getReference().child("chatings/" + roomId + "/" + mData.get(position).getMessage());

                    fileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+mData.get(position).getMessage());
                                if(file.exists()){
                                    showDocumentFile(holder.itemView.getContext(),Environment.DIRECTORY_DOWNLOADS,mData.get(position).getMessage(),file);

                                }
                                else {
                                    DownloadManager.Request request = new DownloadManager.Request(task.getResult());
                                    request.setTitle(mData.get(position).getMessage());
                                    request.setDescription("파일 다운로드중.....");
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mData.get(position).getMessage());
                                    request.setAllowedOverMetered(true);
                                    request.setAllowedOverRoaming(true);

                                    DownloadManager manager = (DownloadManager) holder.itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                    manager.enqueue(request);
                                }
                            }
                        }
                    });
                }
            });
        }
        holder.chatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), Chatalbumclick.class);
                intent.putExtra("image",roomId+"/"+mData.get(position).getMessage());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void showDocumentFile(Context context, String _strPath, String _strFileName,File files)
    {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File file  = files;
        // 파일 확장자별 Mime Type을 지정한다.
        if (_strFileName.endsWith("mp3"))
        {
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
        }
        else if (_strFileName.endsWith("mp4"))
        {
            intent.setDataAndType(Uri.fromFile(file), "vidio/*");
        }
        else if (_strFileName.endsWith("jpg") || _strFileName.endsWith("jpeg") ||
                _strFileName.endsWith("JPG") || _strFileName.endsWith("gif") ||
                _strFileName.endsWith("png") || _strFileName.endsWith("bmp"))
        {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
        }
        else if (_strFileName.endsWith("txt"))
        {
            intent.setDataAndType(Uri.fromFile(file), "text/*");
        }
        else if (_strFileName.endsWith("doc") || _strFileName.endsWith("docx"))
        {
            intent.setDataAndType(Uri.fromFile(file), "application/msword");
        }
        else if (_strFileName.endsWith("xls") || _strFileName.endsWith("xlsx"))
        {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.ms-excel");
        }
        else if (_strFileName.endsWith("ppt") || _strFileName.endsWith("pptx"))
        {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.ms-powerpoint");
        }
        else if (_strFileName.endsWith("pdf")) {
            Uri uri = FileProvider.getUriForFile(context,context.getPackageName()+".provider",file);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout con;
        ImageView profileImage;
        TextView nameText;
        TextView messageText;
        TextView dateText;
        TextView dateMeText;
        ImageView chatImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            con = itemView.findViewById(R.id.chat_bubble_re_layout);
            profileImage = itemView.findViewById(R.id.chat_bubble_re_profile_imageView);
            nameText = itemView.findViewById(R.id.chat_bubble_re_name_textView);
            messageText = itemView.findViewById(R.id.chat_bubble_re_message_textView);
            dateText = itemView.findViewById(R.id.chat_bubble_re_date_textView);
            dateMeText = itemView.findViewById(R.id.chat_bubble_re_me_date_textView);
            chatImageView = itemView.findViewById(R.id.chat_bubble_re_imageView);
            profileImage.setBackground(new ShapeDrawable((new OvalShape())));
            profileImage.setClipToOutline(true);
        }
    }
}

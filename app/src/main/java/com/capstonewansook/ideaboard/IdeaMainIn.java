package com.capstonewansook.ideaboard;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class IdeaMainIn {

    public final static String IDEAMAIN_KEY = "BoardInfo";
    public static void IdeaMainConnect(final Context context, final String boardId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(boardId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult() != null){
                                String uid = task.getResult().get("uid").toString();
                                String title = task.getResult().get("title").toString();
                                String content = task.getResult().get("content").toString();
                                int stars = Integer.parseInt(task.getResult().get("stars").toString());
                                int images = Integer.parseInt(task.getResult().get("images").toString());
                                IdeaMainData mainData = new IdeaMainData(boardId,uid,title,content,stars,images);
                                Intent intent = new Intent(context, IdeamainActivity.class);
                                intent.putExtra(IDEAMAIN_KEY, mainData);
                                context.startActivity(intent);
                                Toast.makeText(context,title,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}

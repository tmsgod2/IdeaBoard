package com.capstonewansook.ideaboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.RankRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.RankRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class RankingFragment extends Fragment {
    private static final String TAG = "RankingFragment";
    public StorageReference mStorageRef;
    public Bitmap bitmap;


    private void ProfileSetting() throws IOException {
        final File localFile = File.createTempFile("images", "jpg");
        StorageReference profileRef = mStorageRef.child("users/"+MainActivity.uid+"/profileImage");
        profileRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ArrayList<RankRecyclerViewData> rankList = new ArrayList<>();

        mStorageRef = FirebaseStorage.getInstance().getReference();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_ranking, container, false);
            db.collection("users").orderBy("stars", Query.Direction.DESCENDING).limit(5).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                if(task.getResult() != null)
                                {
                                 for(QueryDocumentSnapshot snap: task.getResult()) {
                                     if (snap.get("name") != null) {
                                         rankList.add(new RankRecyclerViewData(snap.get("name").toString(), snap.get("office").toString(), Integer.parseInt(snap.get("stars").toString()), snap.getId()));

                                     }
                                 }
                                    final RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                                    RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
                                    recyclerView.setAdapter(adapter);
                                 }


                            }
                        }
                    });

            for(RankRecyclerViewData s : rankList){
                Log.d(TAG,s.getName());
            }

        return  rootView;
    }

}

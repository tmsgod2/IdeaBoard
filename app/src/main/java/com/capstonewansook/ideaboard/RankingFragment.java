package com.capstonewansook.ideaboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class RankingFragment extends Fragment {

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
            db.collection("users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                if(task.getResult() != null)
                                {
                                 for(QueryDocumentSnapshot snap: task.getResult()) {
                                     if (snap.get("name") != null && MainActivity.profileBitmap != null) {
                                         try {
                                             ProfileSetting();
                                             //Log.d( "mvc123", bitmap+"");
                                             rankList.add(new RankRecyclerViewData(MainActivity.profileBitmap, snap.get("name").toString(), snap.get("office").toString(), Integer.parseInt(snap.get("star").toString()), snap.getId()));

                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }

                                     }
                                     else if(snap.get("name") != null && MainActivity.profileBitmap == null) {
                                        // Drawable drawable = getResources().getDrawable(R.drawable.ic_person_black);
                                        // Bitmap bitmapdrawable = ((BitmapDrawable) drawable).getBitmap();
                                        // Bitmap bitmapdrawable  = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_person_black);

                                         rankList.add(new RankRecyclerViewData(MainActivity.profileBitmap, snap.get("name").toString(), snap.get("office").toString(), Integer.parseInt(snap.get("star").toString()), snap.getId()));
                                        // Log.d( "mbc123", bitmapdrawable+"");
                                     }
                                 }


                                 }



                            }
                        }
                    });

        final RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
        recyclerView.setAdapter(adapter);
        return  rootView;
    }

}

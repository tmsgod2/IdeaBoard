package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.postsRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.postsRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostsFragment extends Fragment {
    private FirebaseFirestore db;
    private final static String TAG = null;
    private ArrayList<postsRecyclerViewData> postsList;
    private postsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    public static PostsFragment newInstance(){
        return new PostsFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_posts,container,false);
        db = FirebaseFirestore.getInstance().getInstance();

        postsList = new ArrayList<>();

        db.collection("posts")
                .whereEqualTo("uid", MainActivity.uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                postsList.add(new postsRecyclerViewData(R.drawable.ic_star_gold_24dp,
                                        document.get("title").toString()
                                        ,new SimpleDateFormat("yyyy년 MM월dd일 HH시 mm분").format(((Timestamp)document.get("date")).toDate()),
                                        Integer.parseInt(document.get("stars").toString()),
                                        document.getId()
                                        ,document.get("content").toString()));
                            }
                            adapter = new postsRecyclerViewAdapter(postsList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

//        Log.d(TAG, "되나");
        recyclerView = viewGroup.findViewById(R.id.posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        return viewGroup;

    }

}


package com.capstonewansook.ideaboard;

import android.os.Bundle;
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

import java.util.ArrayList;

public class PostsFragment extends Fragment {
    private FirebaseFirestore db;
    private final static String TAG = null;
    private ArrayList<postsRecyclerViewData> postsList;
    private postsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ViewGroup viewGroup;
    String uid;
    public PostsFragment(String uid){
        this.uid = uid;
    }
    public static PostsFragment newInstance(String uid){
        return new PostsFragment(uid);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_posts,container,false);
        db = FirebaseFirestore.getInstance().getInstance();

        return viewGroup;
    }
    @Override
    public void onStart() {
        super.onStart();
        postsList = new ArrayList<>();
        db.collection("posts")
                .whereEqualTo("uid", uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                postsList.add(new postsRecyclerViewData(document.getId(),R.drawable.ic_star_gold_24dp,
                                        document.get("title").toString() , document.get("content").toString()
                                        ,((Timestamp)document.get("date")).toDate(),
                                        Integer.parseInt(document.get("stars").toString())
                                        ,document.get("content").toString()));
                            }
                            adapter = new postsRecyclerViewAdapter(postsList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

        recyclerView = viewGroup.findViewById(R.id.posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

    }
}


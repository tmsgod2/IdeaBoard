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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StarPostsFragment extends Fragment {
    private FirebaseFirestore db;
    private final static String TAG = null;
    private ArrayList<postsRecyclerViewData> postsList;
    private postsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ViewGroup viewGroup;
    String uid;
    public StarPostsFragment(String uid){
        this.uid = uid;
    }
    public static StarPostsFragment newInstance(String uid){
        return new StarPostsFragment(uid);
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
        db.collection("users")
                .document(uid)
                .collection("Star")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (final QueryDocumentSnapshot document : task.getResult()) {
                            if ((boolean)document.get("star") == true) {
                                FirebaseFirestore.getInstance().getInstance().collection("posts")
                                        .document(document.getId())
                                        .get()

                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                postsList.add(new postsRecyclerViewData(task.getResult().getId(), R.drawable.ic_star_gold_24dp,
                                                        task.getResult().get("title").toString(), task.getResult().get("content").toString()
                                                        ,((Timestamp) task.getResult().get("date")).toDate(),
                                                        Integer.parseInt(task.getResult().get("stars").toString())
                                                        , task.getResult().get("content").toString()));
                                                adapter = new postsRecyclerViewAdapter(postsList);
                                                recyclerView.setAdapter(adapter);
                                            }
                                        });
                            }
                        }
                    }
                });
        recyclerView = viewGroup.findViewById(R.id.posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));
    }
}
package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewData;
import com.capstonewansook.ideaboard.recyclerview.HomeNewideaRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.HomeNewideaRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ImageView manystarPlusImage;
    private ImageView newIdeaImage;
    FirebaseFirestore db;
    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        manystarPlusImage = (ImageView)rootView.findViewById(R.id.home_manystar_plus_imageView);
        manystarPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CleverIdeaBoard.class);
                intent.putExtra("isManystar",true);
                startActivity(intent);
            }
        });

        newIdeaImage = (ImageView)rootView.findViewById(R.id.home_newidea_plus_image);
        newIdeaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CleverIdeaBoard.class);
                intent.putExtra("isManystar",false);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final ArrayList<HomeManystarRecyclerViewData> list = new ArrayList<>();

        db.collection("posts").orderBy("stars", Query.Direction.DESCENDING).limit(5).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult() != null)
                            {
                                for(QueryDocumentSnapshot snap: task.getResult()) {
                                    if (snap.get("title") != null) {
                                        list.add(new HomeManystarRecyclerViewData(snap.getId().toString(),snap.get("title").toString(),Integer.parseInt(snap.get("stars").toString())));

                                    }
                                }
                                RecyclerViewSet(rootView,list, (RecyclerView) rootView.findViewById(R.id.home_manystar_recyclerView),new HomeManystarRecyclerViewAdapter(list));
                            }


                        }
                    }
                });
        final ArrayList<HomeNewideaRecyclerViewData> newlist = new ArrayList<>();
        db.collection("posts").orderBy("date", Query.Direction.DESCENDING).limit(5).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult() != null)
                            {
                                for(QueryDocumentSnapshot snap: task.getResult()) {
                                    if (snap.get("title") != null) {
                                        newlist.add(new HomeNewideaRecyclerViewData(snap.getId().toString(),snap.get("title").toString(), ((Timestamp)snap.get("date")).toDate()));

                                    }
                                }
                                RecyclerViewSet(rootView,newlist, (RecyclerView) rootView.findViewById(R.id.home_newidea_recyclerView),new HomeNewideaRecyclerViewAdapter(newlist));
                            }


                        }
                    }
                });

    }

    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

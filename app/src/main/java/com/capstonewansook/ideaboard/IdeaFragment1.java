package com.capstonewansook.ideaboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.IdeaRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.IdeaRecycletViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class IdeaFragment1 extends Fragment {
    private View view;
    private RecyclerView rv;
    private TextView tv;
    FirebaseFirestore db;
    public StorageReference mStorageRef;


    private ArrayList<IdeaRecycletViewData> arrayList;
    private IdeaRecyclerViewAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchV;


    @Nullable
    @Override
    //

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ideafragment,container,false);
        rv=view.findViewById(R.id.rv_newidea);
        Context context = view.getContext();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();




        db.collection("posts").orderBy("date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult() != null)
                            {
                                for(QueryDocumentSnapshot snap: task.getResult()) {
                                    if (snap.get("date") instanceof Timestamp) {
//                                        Toast.makeText(getActivity(), "환영합니다! " + snap.get("uid").toString(),Toast.LENGTH_LONG).show();
                                        arrayList.add(new IdeaRecycletViewData(snap.get("uid").toString(), snap.get("title").toString(), ((Timestamp)snap.get("date")).toDate(), snap.get("stars").toString(),"1",snap.getId()));

                                    }
                                }
//                                final RecyclerView recyclerView = view.findViewById(R.id.rv_newidea);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                                IdeaRecyclerViewAdapter adapter = new IdeaRecyclerViewAdapter(arrayList);


                                recyclerView = view.findViewById(R.id.rv_newidea);
                                linearLayoutManager = new LinearLayoutManager(view.getContext());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                mainAdapter = new IdeaRecyclerViewAdapter(arrayList);
                                recyclerView.setAdapter(mainAdapter);
                            }


                        }
                    }
                });







        recyclerView = view.findViewById(R.id.rv_newidea);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new IdeaRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);
//        arrayList.add( new IdeaRecycletViewData(R.drawable.idea_profile,"안드러이드","2000.1.1","13","10"));

        return view;
    }
//
    public void StartAdapter(String str){
        mainAdapter.getFilter().filter(str);
    }
}

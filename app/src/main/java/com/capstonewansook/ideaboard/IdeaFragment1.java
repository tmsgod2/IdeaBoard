package com.capstonewansook.ideaboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class IdeaFragment1 extends Fragment {
    private View view;
    private RecyclerView rv;
    private TextView tv;
    FirebaseFirestore db;
    FirebaseFirestore db2;
    public StorageReference mStorageRef;
    String TAG = "aaaaaaaaa";

    int comNum;


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
        db2 = FirebaseFirestore.getInstance();





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

//                                        arrayList.add(new IdeaRecycletViewData(snap.get("uid").toString(), snap.get("title").toString(), ((Timestamp)snap.get("date")).toDate(), snap.get("stars").toString(), Integer.toString(CommentCal(snap.getId())),snap.getId()));
                                        CommentCal(snap.get("uid").toString(), snap.get("title").toString(), ((Timestamp)snap.get("date")).toDate(), snap.get("stars").toString(),snap.getId());

                                    }
                                }

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

        return view;
    }
//
    public void StartAdapter(String str){
        mainAdapter.getFilter().filter(str);
    }



    public void CommentCal(final String iv_profill, final String tv_story, final Date tv_day, final String tv_recomand, final String id){
        comNum = 0;
        Log.d(TAG, id +"???????\n");

        db.collection("posts").document(id).collection("comments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult() != null)
                            {


                                for(QueryDocumentSnapshot snap: task.getResult()) {
                                    comNum = comNum+ 1;
                                    Log.d(TAG, comNum + "???????\n");
                                }
//
                            }

                        }
                        arrayList.add(new IdeaRecycletViewData(iv_profill, tv_story, tv_day, tv_recomand,Integer.toString(comNum),id));
                        comNum = 0;

                        recyclerView = view.findViewById(R.id.rv_newidea);
                        linearLayoutManager = new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        Collections.sort(arrayList, new Comparator<IdeaRecycletViewData>() {
                            @Override
                            public int compare(IdeaRecycletViewData o1, IdeaRecycletViewData o2) {
                               return o1.getTv_day().compareTo(o2.getTv_day());
                            }
                        });
                        Collections.reverse(arrayList);
                        mainAdapter = new IdeaRecyclerViewAdapter(arrayList);
                        recyclerView.setAdapter(mainAdapter);

                    }



                });


//

    }

}

package com.capstonewansook.ideaboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.IdeaRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.IdeaRecycletViewData;

import java.util.ArrayList;

public class IdeaFragment2 extends Fragment {
    private View view;
    private RecyclerView rv;
    private TextView tv;

    private ArrayList<IdeaRecycletViewData> arrayList;
    private IdeaRecyclerViewAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ideafragment2,container,false);
        rv=view.findViewById(R.id.rv_newidea);
        Context context = view.getContext();




        recyclerView = view.findViewById(R.id.rv_newidea);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new IdeaRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);
        arrayList.add( new IdeaRecycletViewData(R.drawable.idea_profile,"안드러이드","2011.1.1",R.drawable.heart,R.drawable.comment,"13","10"));

        return view;
    }
}

package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RankingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_ranking, container, false);

        ArrayList<RankRecyclerViewData> rankList = new ArrayList<>();
        rankList.add(new RankRecyclerViewData(R.drawable.ic_person_black,"아선행","동의대학교",100));
        rankList.add(new RankRecyclerViewData(R.drawable.ic_person_black,"신호준","신라대학교",90));
        rankList.add(new RankRecyclerViewData(R.drawable.ic_person_black,"박정우","서울대학교",80));
        rankList.add(new RankRecyclerViewData(R.drawable.ic_person_black,"이성재","부산대학교",70));
        rankList.add(new RankRecyclerViewData(R.drawable.ic_person_black,"모르는분","삼성sds",60));

        final RecyclerView recyclerView = rootView.findViewById(R.id.ranking_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    TextView name = (TextView)rv.getChildViewHolder(child).itemView.findViewById(R.id.rank_re_name_textView);
                    Toast.makeText(rootView.getContext(), "이름"+name.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return  rootView;
    }
}

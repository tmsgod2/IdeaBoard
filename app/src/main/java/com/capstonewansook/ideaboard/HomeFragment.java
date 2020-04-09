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


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<HomeManystarRecyclerViewData> list = new ArrayList<>();

        list.add(new HomeManystarRecyclerViewData("인기 많은 아이디어", 100));
        list.add(new HomeManystarRecyclerViewData("젤 많은 아이디어", 142));
        list.add(new HomeManystarRecyclerViewData("인기 없는 아이디어", 10));
        list.add(new HomeManystarRecyclerViewData("적당한 아이디어", 39));
        list.add(new HomeManystarRecyclerViewData("적당적당한 아이디어", 28));

        RecyclerView manystarRecyclerView = rootView.findViewById(R.id.home_manystar_recyclerView);
        manystarRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        HomeManystarRecyclerViewAdapter manystarAdapter = new HomeManystarRecyclerViewAdapter(list);
        manystarRecyclerView.setAdapter(manystarAdapter);

        manystarRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    TextView name = (TextView)rv.getChildViewHolder(child).itemView.findViewById(R.id.home_re_manystar_title_textview);
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

        return rootView;
    }
}

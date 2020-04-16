package com.capstonewansook.ideaboard;

import android.content.Intent;
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

import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewData;
import com.capstonewansook.ideaboard.recyclerview.HomeNewideaRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.HomeNewideaRecyclerViewData;

import java.util.ArrayList;
import java.util.Date;


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

        ArrayList<HomeNewideaRecyclerViewData> list1 = new ArrayList<>();
        list1.add(new HomeNewideaRecyclerViewData("가장 최근 아이디어", new Date(System.currentTimeMillis())));
        list1.add(new HomeNewideaRecyclerViewData("2 아이디어", new Date(System.currentTimeMillis())));
        list1.add(new HomeNewideaRecyclerViewData("3 아이디어", new Date(System.currentTimeMillis())));
        list1.add(new HomeNewideaRecyclerViewData("4 아이디어", new Date(System.currentTimeMillis())));
        list1.add(new HomeNewideaRecyclerViewData("5 아이디어", new Date(System.currentTimeMillis())));

        RecyclerViewSet(rootView,list, (RecyclerView) rootView.findViewById(R.id.home_manystar_recyclerView),new HomeManystarRecyclerViewAdapter(list),R.id.home_re_manystar_title_textview);
        RecyclerViewSet(rootView,list1,(RecyclerView) rootView.findViewById(R.id.home_newidea_recyclerView),new HomeNewideaRecyclerViewAdapter(list1),R.id.home_re_newidea_title_textView);

        return rootView;
    }

    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    TextView name = (TextView)rv.getChildViewHolder(child).itemView.findViewById(toastPosition);
                    Toast.makeText(view.getContext(), "이름"+name.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),IdeamainActivity.class);
                    startActivity(intent);
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
    }
}

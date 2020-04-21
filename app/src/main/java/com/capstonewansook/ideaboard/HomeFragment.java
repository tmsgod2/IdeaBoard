package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.HomeManystarRecyclerViewData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ImageView manystarPlusImage;
    private ImageView newIdeaImage;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        ArrayList<HomeManystarRecyclerViewData> list = new ArrayList<>();

        list.add(new HomeManystarRecyclerViewData("asdasdasd","인기 많은 아이디어", 100));

        RecyclerViewSet(rootView,list, (RecyclerView) rootView.findViewById(R.id.home_manystar_recyclerView),new HomeManystarRecyclerViewAdapter(list),R.id.home_re_manystar_title_textview);
//        RecyclerViewSet(rootView,list1,(RecyclerView) rootView.findViewById(R.id.home_newidea_recyclerView),new HomeNewideaRecyclerViewAdapter(list1),R.id.home_re_newidea_title_textView);

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

    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

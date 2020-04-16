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

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    public static PostsFragment newInstance(){
        return new PostsFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_posts,container,false);

        ArrayList<postsRecyclerViewData> postsList = new ArrayList<>();
        postsList.add(new postsRecyclerViewData(R.drawable.bt_camera,R.drawable.ic_star_gold_24dp,"2020,4,13","이거 개쩜",15));
        postsList.add(new postsRecyclerViewData(R.drawable.bt_camera,R.drawable.ic_star_gold_24dp,"2020,4,13","이거 개쩜",15));
        postsList.add(new postsRecyclerViewData(R.drawable.bt_camera,R.drawable.ic_star_gold_24dp,"2020,4,13","이거 개쩜",15));
        postsList.add(new postsRecyclerViewData(R.drawable.bt_camera,R.drawable.ic_star_gold_24dp,"2020,4,13","이거 개쩜",15));

        RecyclerView recyclerView = viewGroup.findViewById(R.id.posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        postsRecyclerViewAdapter adapter = new postsRecyclerViewAdapter(postsList);

        recyclerView.setAdapter(adapter);
        return viewGroup;

    }

}


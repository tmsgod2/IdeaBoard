package com.capstonewansook.ideaboard;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class ProfileFragment extends Fragment {

    TextView postTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView imageView = rootView.findViewById(R.id.profile_imageView);
        imageView.setBackground(new ShapeDrawable((new OvalShape())));
        imageView.setClipToOutline(true);
        postTextView = rootView.findViewById(R.id.profile_post_textView);
        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asd","터치");
                ((MainActivity)getActivity()).replaceFragment(PostsFragment.newInstance());
            }
        });
        // Inflate the layout for this fragment
        return rootView;

    }
}

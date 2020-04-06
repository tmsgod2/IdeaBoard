package com.capstonewansook.ideaboard;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;



public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView imageView = rootView.findViewById(R.id.profile_imageView);
        imageView.setBackground(new ShapeDrawable((new OvalShape())));
        imageView.setClipToOutline(true);
        // Inflate the layout for this fragment
        return rootView;

    }
}

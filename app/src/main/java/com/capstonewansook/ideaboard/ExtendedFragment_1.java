package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.net.URISyntaxException;


public class ExtendedFragment_1 extends Fragment {
    String uri;
    String imageuri;
    
    public ExtendedFragment_1(String uri){
        this.uri = uri;
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extended_1, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        if(getArguments() != null) {
            Bundle args = getArguments();
            imageuri = args.getString("imaRes");
            Log.d("my123",imageuri);
            StorageReference ref = FirebaseStorage.getInstance().getReference("image/" + imageuri);
            Glide.with(this).load(ref).into(imageView);

        }
        return  view;
    }
}

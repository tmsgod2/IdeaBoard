package com.capstonewansook.ideaboard;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class ProfileFragment extends Fragment {

    private TextView nameTextView;
    private TextView stateTextView;
    private TextView officeTextView;
    private TextView postTextView;
    private TextView wantTextView;
    private String name;
    private String office;
    private boolean ismine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imageView = rootView.findViewById(R.id.profile_imageView);
        imageView.setBackground(new ShapeDrawable((new OvalShape())));
        imageView.setClipToOutline(true);

        nameTextView = rootView.findViewById(R.id.profile_name_textView);
        nameTextView.setText(MainActivity.cus.getName());

        stateTextView = rootView.findViewById(R.id.profile_state_textView);
        stateTextView.setText(MainActivity.cus.getState());

        officeTextView = rootView.findViewById(R.id.profile_office_textView);
        officeTextView.setText(MainActivity.cus.getOffice());

        postTextView = rootView.findViewById(R.id.profile_post_textView);
        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment post = PostsFragment.newInstance();
                ((MainActivity)getActivity()).replaceFragment(post);
                 MainActivity.fragmentStack.push(new FragmentData(post,R.id.profile_menu));
            }
        });

        wantTextView = rootView.findViewById(R.id.profile_want_idea_textView);
        wantTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment post = PostsFragment.newInstance();
                ((MainActivity)getActivity()).replaceFragment(post);
                MainActivity.fragmentStack.push(new FragmentData(post,R.id.profile_menu));
            }
        });
        // Inflate the layout for this fragment
        return rootView;

    }

}

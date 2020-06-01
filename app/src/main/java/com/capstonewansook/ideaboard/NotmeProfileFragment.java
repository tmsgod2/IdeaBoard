package com.capstonewansook.ideaboard;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.capstonewansook.ideaboard.ChatroomIn.ChatroomCreate;
import static com.capstonewansook.ideaboard.ChatroomIn.ChatroomOpen;
import static com.capstonewansook.ideaboard.ChatroomIn.isChatroomExist;

public class NotmeProfileFragment extends Fragment {

    private String uid;
    private  int chekroom;
    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView stateTextView;
    private TextView officeTextView;
    private TextView postTextView;
    private TextView wantTextView;
    private TextView profileStar;
    private int star;
    private FloatingActionButton chatButton;
    FirebaseFirestore db;
    final ArrayList<String> uids = new ArrayList<>();
    final ArrayList<String> uids2 = new ArrayList<>();
    ChatroomData chatroomData;

    public NotmeProfileFragment(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        profileImageView = rootView.findViewById(R.id.profile_imageView);
        nameTextView = rootView.findViewById(R.id.profile_name_textView);
        stateTextView = rootView.findViewById(R.id.profile_state_textView);
        officeTextView = rootView.findViewById(R.id.profile_office_textView);
        postTextView = rootView.findViewById(R.id.profile_post_textView);
        wantTextView = rootView.findViewById(R.id.profile_want_idea_textView);
        chatButton = rootView.findViewById(R.id.notme_profile_chat_button);
        profileStar = rootView.findViewById(R.id.profileStar);
        chatButton.setVisibility(View.VISIBLE);
        db = FirebaseFirestore.getInstance();

        rootView.findViewById(R.id.profile_update_button).setVisibility(View.GONE);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult() != null){
                        DocumentSnapshot cusData = task.getResult();
                        nameTextView.setText(cusData.get("name").toString());
                        stateTextView.setText(cusData.get("state").toString());
                        officeTextView.setText(cusData.get("office").toString());

                    }
                }
            }
        });

        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"+uid+"/profileImage");
        profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(container.getContext())
                            .load(task.getResult())
                            .into(profileImageView);
                } else {
                    profileImageView.setImageResource(R.drawable.ic_person_black_24dp);
                }
            }
        });
        profileImageView.setBackground(new ShapeDrawable((new OvalShape())));
        profileImageView.setClipToOutline(true);

        star = 0;
        FirebaseFirestore.getInstance().collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            if (snapshot.get("uid").equals(uid))
                                star += Integer.parseInt(snapshot.get("stars").toString());
                        }
                        profileStar.setText(star+"");
                    }
                }
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rid = isChatroomExist(uid);
                if(rid.equals("none")){
                    ChatroomCreate(chatButton.getContext(),uid, nameTextView.getText().toString());
                }else{
                    ChatroomOpen(chatButton.getContext(),rid,uid,nameTextView.getText().toString());
                }
            }
        });

        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment post = PostsFragment.newInstance(uid);
                ((MainActivity)getActivity()).replaceFragment(post);
            }
        });

        wantTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment post = StarPostsFragment.newInstance(uid);
                ((MainActivity)getActivity()).replaceFragment(post);
            }
        });

        return rootView;

    }

    public static NotmeProfileFragment newInstance(String uid) { return new NotmeProfileFragment(uid);}
}

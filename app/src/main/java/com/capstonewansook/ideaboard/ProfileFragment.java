package com.capstonewansook.ideaboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class ProfileFragment extends Fragment {

    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView stateTextView;
    private TextView officeTextView;
    private TextView postTextView;
    private TextView wantTextView;
    private static final int RC_IMAGE_CHANGE = 1001;
    private static final int RC_EXTERNAL_STORAGE_PERMISSION = 2001;
    private StorageReference mStorageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RC_EXTERNAL_STORAGE_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


        try {
            ProfileWatch();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profileImageView = rootView.findViewById(R.id.profile_imageView);
        profileImageView.setBackground(new ShapeDrawable((new OvalShape())));
        profileImageView.setClipToOutline(true);
        profileImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProfileImageChange();
                return true;
            }
        });

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

    private void ProfileWatch() throws IOException {
        final File localFile = File.createTempFile("images", "jpg");
        StorageReference profileRef = mStorageRef.child("users/"+MainActivity.uid+"/profileImage");
        profileRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profileImageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }
    private void ProfileImageChange(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_IMAGE_CHANGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_IMAGE_CHANGE:
                Uri image = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                    profileImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                StorageReference profileRef = mStorageRef.child("users/"+MainActivity.uid+"/profileImage");

                profileRef.putFile(image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
//                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Log.d("asd", taskSnapshot.toString());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });
                return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

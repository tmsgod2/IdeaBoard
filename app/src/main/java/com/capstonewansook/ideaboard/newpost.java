package com.capstonewansook.ideaboard;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class newpost extends AppCompatActivity {
    private final static String TAG = null;
    private FirebaseFirestore db;
    private Button newbutton;
    private FloatingActionButton imagebutton;
    private EditText title;
    private EditText contents;
    private Intent intent;
    private ClipData clipData;
    private Context context;
    private LinearLayout linearLayout;
    private ArrayList<Uri> imguri;
    private int count;
    public StorageReference postsimage = FirebaseStorage.getInstance().getReference();
    private String postid;
    private RelativeLayout loadinglayout;
    private DocumentReference docref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("새 아이디어");
        actionBar.setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();

        newbutton = (Button) findViewById(R.id.newpostbutton);
        imagebutton = (FloatingActionButton) findViewById(R.id.newpost_addImage_Button);
        title = (EditText) findViewById(R.id.newposteditText1);
        contents = (EditText) findViewById(R.id.newposteditText2);
        linearLayout = (LinearLayout) findViewById((R.id.postimagelayout));
        context = this;
        imguri = new ArrayList<Uri>();
        count = 0;
        loadinglayout = (RelativeLayout) findViewById(R.id.loadingLayout);


        newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().equals("") && !contents.getText().toString().equals("")) {
                    NewpostData newpostData = new NewpostData(MainActivity.uid, title.getText().toString(), contents.getText().toString(),(Object) FieldValue.serverTimestamp(),imguri.size());
                    DBCreate(newpostData);



                    loadinglayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "빈칸을 입력해주세요!", Toast.LENGTH_LONG).show();
                }
            }
        });
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission(); //권한 가져오기
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(clipData != null)
            newimagebutton();
    }

    //상단의 뒤로가기 버튼 클릭시 뒤로 감
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void DBCreate(NewpostData newData){
        db.collection("posts")
                .add(newData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        postid = documentReference.getId();
                        if(clipData != null)
                        {
                            upimg();
                        }

                        onBackPressed();
                        loadinglayout.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }
    public void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        2001);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (data.getClipData() != null) {
                //Uri image = data.getData();
                clipData = data.getClipData();
                for(int i = 0; i<clipData.getItemCount();i++)
                {
                    imguri.add(clipData.getItemAt(i).getUri());
                }
            }
        }
    }
    protected void upimg() {
        int j = 0;
        for (int i = 0; i < imguri.size(); i++) {
            try {

                if (imguri.get(i) != null) {
                    Uri image = imguri.get(i);
                    StorageReference riverseRef = postsimage.child("posts/" + postid + "/image" + j);
                    j++;
                    riverseRef.putFile(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    protected  void newimagebutton() {
        for (int i = 0 ;i < clipData.getItemCount(); i++) {
            final Button but = new Button(context);
            but.setText("image " + count + " X");
            but.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,100));
            but.setMinWidth(0);
            but.setTextSize(11);
            but.setId(count);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imguri.set(but.getId(),null);
                    linearLayout.removeView(but);
                }
            });
            count += 1;
            linearLayout.addView(but);
        }
    }
}

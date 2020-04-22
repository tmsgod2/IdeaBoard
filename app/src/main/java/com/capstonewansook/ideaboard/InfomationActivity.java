package com.capstonewansook.ideaboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class InfomationActivity extends AppCompatActivity {

    private String uid;
    private boolean isSame=true;
    private Button nicknameSameCheckButton;
    private EditText nicknameEditText;
    private EditText locateEditText;
    private EditText officeEditText;
    private EditText stateEditText;
    private Spinner jobSpinner;
    private Button okButton;
    private Button cancleButton;
    private FirebaseFirestore db;

    private static ArrayList<String> nicknameArray = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costomer_infomation);

        //로그인 한 사람 uid 저장
        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        Toast.makeText(this,uid,Toast.LENGTH_LONG);

        //파이어스토어 연결
        db = FirebaseFirestore.getInstance();

        //뷰 연결
        nicknameEditText = (EditText)findViewById(R.id.infomation_nickname_editText);
        locateEditText = (EditText)findViewById(R.id.infomation_location_editText);
        officeEditText = (EditText)findViewById(R.id.infomation_office_editText);
        stateEditText = (EditText) findViewById(R.id.infomation_state_editText);

        //Spinner에 배열 넣기
        Resources res = getResources();
        String[] jobItem = res.getStringArray(R.array.infomation_job_array);
        jobSpinner =(Spinner)findViewById(R.id.infomation_job_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                jobItem
        );
        jobSpinner.setAdapter(adapter);
        jobSpinner.setSelection(0);

        nicknameArrayInit();

        nicknameSameCheckButton = (Button)findViewById(R.id.infomation_sameCheck_button);
        nicknameSameCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = nicknameEditText.getText().toString();
                if(!nickname.equals("")){
                    if(nicknameArray.isEmpty()){
                        Toast.makeText(getApplicationContext(),"사용 가능합니다!",Toast.LENGTH_LONG).show();
                        isSame = false;
                    }
                    else{
                        if(nicknameArray.contains(nickname)){
                            Toast.makeText(getApplicationContext(),"다른 닉네임을 사용하십시오!", Toast.LENGTH_LONG).show();
                            isSame = true;
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"사용 가능합니다!",Toast.LENGTH_LONG).show();
                            isSame = false;
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"닉네임을 입력하세요",Toast.LENGTH_LONG).show();
                    isSame = false;
                }
            }
        });
        okButton = (Button)findViewById(R.id.infomation_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditTextIsEmpty()){
                    final CustomerData cus = new CustomerData(nicknameEditText.getText().toString(),
                            locateEditText.getText().toString(), officeEditText.getText().toString(),
                            jobSpinner.getSelectedItem().toString(),stateEditText.getText().toString(),
                            0);
                    db.collection("users").document(uid)
                            .set(cus)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"환영합니다." + cus.getName()+" 님",Toast.LENGTH_LONG).show();
                                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    mainIntent.putExtra("CustomerData",cus);
                                    mainIntent.putExtra("UID",uid);
                                    startActivity(mainIntent);
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(), "필수 입력칸을 입력해주세요!", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancleButton = (Button)findViewById(R.id.infomation_cancle_button);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancle();
            }
        });

    }


    private void nicknameArrayInit(){
        db.collection("users")
                .whereEqualTo("isFirst",false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            try {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    nicknameArray.add(document.getString("name"));
                                }
                                Collections.sort(nicknameArray);
                            }
                            catch (Exception e){}
                        }
                    }
                });

    }

    private boolean EditTextIsEmpty(){
        if(nicknameEditText.getText().toString().equals("")){
            return true;
        }
        if(locateEditText.getText().toString().equals("")){
            return true;
        }
        if(isSame){
            Toast.makeText(getApplicationContext(),"닉네임 중복 체크를 해주세요!", Toast.LENGTH_LONG);
            return true;
        }

        return false;
    }

    private void Cancle(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("취소 하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {

    private String uid;
    private boolean isSame=false;
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
    private CustomerData currentCus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costomer_infomation);

        //로그인 한 사람 uid 저장
        final Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        currentCus = (CustomerData)intent.getSerializableExtra("CustomerData");
        Toast.makeText(this,uid,Toast.LENGTH_LONG);

        //파이어스토어 연결
        db = FirebaseFirestore.getInstance();

        //뷰 연결
        nicknameEditText = (EditText)findViewById(R.id.infomation_nickname_editText);
        nicknameEditText.setText(currentCus.getName());
        nicknameEditText.setEnabled(false);
        locateEditText = (EditText)findViewById(R.id.infomation_location_editText);
        locateEditText.setText(currentCus.getLocate());
        officeEditText = (EditText)findViewById(R.id.infomation_office_editText);
        officeEditText.setText(currentCus.getOffice());
        stateEditText = (EditText) findViewById(R.id.infomation_state_editText);
        stateEditText.setText(currentCus.getState());

        //Spinner에 배열 넣기
        Resources res = getResources();
        final String[] jobItem = res.getStringArray(R.array.infomation_job_array);
        Arrays.sort(jobItem);
        jobSpinner =(Spinner)findViewById(R.id.infomation_job_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                jobItem
        );
        jobSpinner.setAdapter(adapter);
        jobSpinner.setSelection(Arrays.binarySearch(jobItem,currentCus.getJob()));

        nicknameArrayInit();

        nicknameSameCheckButton = (Button)findViewById(R.id.infomation_sameCheck_button);
        nicknameSameCheckButton.setVisibility(View.GONE);

        okButton = (Button)findViewById(R.id.infomation_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditTextIsEmpty()){
                    Map<String,Object> updateCus =  new HashMap<>();
                    updateCus.put("state",stateEditText.getText().toString());
                    updateCus.put("locate",locateEditText.getText().toString());
                    updateCus.put("office",officeEditText.getText().toString());
                    updateCus.put("job",jobSpinner.getSelectedItem().toString());
                    final Map<String,Object> putUpdateCus = updateCus;
                    db.collection("users").document(uid)
                            .set(updateCus, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent profileIntent = new Intent();
                                    profileIntent.putExtra("UpdateCustomerData", (Serializable) putUpdateCus);
                                    setResult(RESULT_OK,profileIntent);
                                    finish();
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
                        setResult(RESULT_CANCELED);
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

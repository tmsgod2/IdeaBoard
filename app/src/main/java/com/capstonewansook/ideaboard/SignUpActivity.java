package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passEditText;
    private EditText passConEditText;
    private Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText)findViewById(R.id.signup_email_editText);
        passEditText = (EditText)findViewById(R.id.signup_passwd_editText);
        passConEditText = (EditText)findViewById(R.id.signup_passwd_confirm_editText);

        signupButton = (Button)findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty()){
                    String email = emailEditText.getText().toString();
                    String password = passEditText.getText().toString();
                    SignUp(email,password);
                }
            }
        });
    }

    private boolean isEmpty(){

        if(emailEditText.getText().toString().equals("")){
            Snackbar.make(this.emailEditText,"이메일을 입력하세요",Snackbar.LENGTH_SHORT).show();
            return true;
        }
        if(passEditText.getText().toString().equals("")){
            Snackbar.make(this.passEditText,"비밀번호를 입력하세요",Snackbar.LENGTH_SHORT).show();
            return true;
        }
        if(passConEditText.getText().toString().equals("")){
            Snackbar.make(this.passConEditText,"비밀번호 확인을 입력하세요",Snackbar.LENGTH_SHORT).show();
            return true;
        }
        if(!passEditText.getText().toString().equals(passConEditText.getText().toString())){
            Snackbar.make(this.passEditText,"비밀번호가 일치하지 않습니다!",Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }

    private void SignUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmail:success");
                            finish();
                        }else{
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"인증에 실패했습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

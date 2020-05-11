package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class logintitle extends AppCompatActivity {

    private final static String TAG = "로그인";
    Button facebookButton;
    Button kakaoButton;
    //파이어베이스 인증 변수
    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_SIGN_UP = 8001;
    private TextView signUpTextView;
    private Button eSignInButton;
    private SignInButton gSignInButton;
    private FirebaseFirestore db;
    private CustomerData cus;
    public static boolean isNewCustomer = false;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logintitle);

        progressBar = (ProgressBar)findViewById(R.id.login_progressBar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("233751859976-jt8md2f464ipgtp3jid6m8ghjrp4eklg.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //인증 초기화
        mAuth = FirebaseAuth.getInstance();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        db = FirebaseFirestore.getInstance();

        emailEditText = (EditText)findViewById(R.id.login_email_editText);
        passwordEditText = (EditText)findViewById(R.id.login_passwd_editText);

        signUpTextView = (TextView)findViewById(R.id.login_signup_textView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


        eSignInButton = (Button)findViewById(R.id.login_email_button);
        eSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.equals("")){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.equals("")){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Progressing();
                EmailSignIn(email,password);

            }
        });

        gSignInButton = findViewById(R.id.googlelogin);
        gSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progressing();
                GooglesignIn();
            }
        });

        Progressing();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void EmailSignIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, user.getEmail()+"");
                            updateUI(user);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "인증에 실패했습니다.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void GooglesignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e){
                Toast.makeText(getApplicationContext(),"구글 로그인 실패" + e.getMessage(),Toast.LENGTH_LONG).show();
                ProgressEnd();
            }

        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"구글 로그인 실패", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            ProgressEnd();
                        }
                    }
                });
    }

    private void updateUI(final FirebaseUser user){
        if(user != null){
            DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String uid = mAuth.getUid();
                    cus = documentSnapshot.toObject(CustomerData.class);
                    try {
                        if (cus.getisFirst()) {
                            Intent intent = new Intent(getApplication(), InfomationActivity.class);
                            intent.putExtra("UID", uid);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            intent.putExtra("UID", uid);
                            intent.putExtra("CustomerData", cus);
                            startActivity(intent);
                        }
                        ProgressEnd();
                    }
                    catch (NullPointerException e){
                        DBCreate(user);
                        updateUI(user);
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "실패 " + mAuth.getCurrentUser().getUid().toString());

                }
            });

        }else{
            ProgressEnd();
        }
    }

    private void DBCreate(FirebaseUser user) {

        db.collection("users")
                .document(user.getUid())
                .set(new CustomerData())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "등록 완료");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "에러",e);
                    }
                });
    }
    private void Progressing(){
        progressBar.setVisibility(View.VISIBLE);
        emailEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        eSignInButton.setEnabled(false);
        gSignInButton.setEnabled(false);
        signUpTextView.setEnabled(false);
    }

    private void ProgressEnd(){
        progressBar.setVisibility(View.GONE);
        emailEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        eSignInButton.setEnabled(true);
        gSignInButton.setEnabled(true);
        signUpTextView.setEnabled(true);
    }

}

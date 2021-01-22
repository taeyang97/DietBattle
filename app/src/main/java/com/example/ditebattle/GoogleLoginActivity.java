package com.example.ditebattle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLoginActivity extends AppCompatActivity {
    private SignInButton googleSignInBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth = null;
    private Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        googleSignInBtn = findViewById(R.id.googleSignInBtn);
        googleSignInBtn.setSize(SignInButton.SIZE_STANDARD);
        googleSignInBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                switch(v.getId()){
                    case R.id.googleSignInBtn:

                        signIn();
                }
            }
        });
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                switch(v.getId()){
                    case R.id.btn_logout:
                        signOut();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("test","실험");

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Toast.makeText(getApplicationContext(), "signIn", Toast.LENGTH_SHORT).show();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "캐치되버림"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //handleSignInResult(task);
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(GoogleLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.e("여기가문제",""+task.getException());

                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
//        try{
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            updateUI(account);
//        } catch(ApiException e){
//            Log.w("signIn","signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
//        }
//    }

    private void updateUI(FirebaseUser account){
        if (account !=null){
            Toast.makeText(getApplicationContext(), "successfully signed in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GoogleLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "failed to sign in ", Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "로그아웃됨", Toast.LENGTH_SHORT).show();
    }
}
package com.example.com.entregable.View.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.entregable.Model.POJO.Account;
import com.example.com.entregable.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private EditText etEmail;
    private EditText etPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView createAccount = findViewById(R.id.am_tv_crearCuenta);
        etEmail = findViewById(R.id.am_teet_emailTexto);
        etPass = findViewById(R.id.am_teet_passTexto);
        TextView loginAccount = findViewById(R.id.am_tv_loginCuenta);
        getSupportActionBar().setTitle("Ingreso");

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Account account = accountMaker();
                if(account.validLogin()) createAccount(account);
            }
        });

        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = accountMaker();
                if(account.validLogin()) loginAccount(account);
                else Toast.makeText(MainActivity.this, "No pueden haber valores vacios", Toast.LENGTH_SHORT).show();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(MainActivity.this, "Hubo un problema al comunicarse con Facebook.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Account accountMaker(){
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        return new Account(email, pass);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userExists();
    }

    private void iniciarExhibicion(){
        Intent intent = new Intent(this, ExhibicionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_izq_in, R.anim.slide_izq_out);
    }

    private void createAccount(Account account){
        mAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("CREATEACCOUNT", "createUserWithEmail:success");
                            iniciarExhibicion();
                        } else {
                            Log.w("CREATEACCOUNT", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Ex: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginAccount(Account account){
        mAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LOGIN", "signInWithEmail:success");
                            iniciarExhibicion();
                        } else {
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("HANDLEFB", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("HANDLEFB", "signInWithCredential:success");
                            iniciarExhibicion();
                        } else {
                            Log.w("HANDLEFB", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void userExists(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            iniciarExhibicion();
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken != null && !accessToken.isExpired()) {
            iniciarExhibicion();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

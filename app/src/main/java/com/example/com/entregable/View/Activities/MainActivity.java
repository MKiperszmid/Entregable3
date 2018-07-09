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
    private LoginButton loginButton;
    private TextView createAccount;
    private EditText etEmail;
    private EditText etPass;
    private TextInputLayout emailContainer;
    private TextInputLayout passContainer;
    private TextView loginAccount;

    private static final String EMAIL = "email";
    private FirebaseAuth mAuth;
    private String nombreSeccion = "Ingreso";

    /*
     * Mostramos cardview con nombre de pintura, pero no de Artista.
     * Lo que sabemos de la Paint, es su foto, su nombre, y su ArtistId. (La foto, el path. Asique esto se aplica con Storage)
     * Cuando clickeamos la foto, deberiamos hacer un artistController.grabArtists, que le pase el ID del artista (con un getArtistId en la Paint)
     * Y cargar la informacion, basado en lo que obtenemos.
     *
     * Agarrar una lista de Artistas, en este entregable, no es necesario. Ya que no lo pide en ningun momento.
     *
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAccount = findViewById(R.id.am_tv_crearCuenta);
        etEmail = findViewById(R.id.am_teet_emailTexto);
        etPass = findViewById(R.id.am_teet_passTexto);
        emailContainer = findViewById(R.id.am_til_emailContainer);
        passContainer = findViewById(R.id.am_til_passContainer);
        loginAccount = findViewById(R.id.am_tv_loginCuenta);

        getSupportActionBar().setTitle(nombreSeccion);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                if(!email.contains("@")){
                    emailContainer.setError("Falta un @ en email!");
                    return;
                }

                createAccount(email, pass);
            }
        });

        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                loginAccount(email, pass);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton =  findViewById(R.id.login_button);
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

    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CREATEACCOUNT", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            iniciarExhibicion();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CREATEACCOUNT", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Ex: " + task.getException(), Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void loginAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            iniciarExhibicion();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("HANDLEFB", "signInWithCredential:success");
                            iniciarExhibicion();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("HANDLEFB", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
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

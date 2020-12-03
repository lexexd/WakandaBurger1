package com.example.wakandaburger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.List;

public class Login extends Registry_Activity {

    FirebaseAuth mfirebaseAutH;
    FirebaseAuth.AuthStateListener mAutListener;

    public static final int REQUEST_CODE = 1234;

    List<AuthUI.IdpConfig> provider = Arrays.asList(new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(), new AuthUI.IdpConfig.EmailBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        mfirebaseAutH = FirebaseAuth.getInstance();
        mAutListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser Users = firebaseAuth.getCurrentUser();
                if (Users != null) {
                    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider)
                    .setIsSmartLockEnabled(false).build(),REQUEST_CODE);
                }
            }

        };


    }

    @Override
    protected void onResume() {
        super.onResume();
    mfirebaseAutH.addAuthStateListener(mAutListener);
    }
    @Override
    protected void onPause() {
mfirebaseAutH.removeAuthStateListener(mAutListener);
        super.onPause();
    }


    public void cerrarsecion(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(Login.this,"sesion cerrada", Toast.LENGTH_SHORT).show();
            finish();
            }
        });
    }
}
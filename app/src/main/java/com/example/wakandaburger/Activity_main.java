package com.example.wakandaburger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

public class Activity_main extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonRegister;

    //VARIABLES DE DATOS A REGISTRAR
    private String Name="";
    private String Email="";
    private String Password="";
    FirebaseAuth mAut;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAut = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mEditTextName= findViewById(R.id.editTextName);
        mEditTextEmail= findViewById(R.id.editTextEmail);
        mEditTextPassword= findViewById(R.id.editTextPassword);

        mButtonRegister= findViewById(R.id.btnRegister);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {

            @Override


            public void onClick(View v) {
            Name=mEditTextName.getText().toString();
            Email=mEditTextEmail.getText().toString();
            Password=mEditTextPassword.getText().toString();

            if(!Name.isEmpty()&& !Email.isEmpty()&& !Password.isEmpty()){
                if(Password.length()>=6){
                    registerUser();
                }
                else{
                    Toast.makeText(Activity_main.this, "la contraseña debe tener al menos 6 digitos", Toast.LENGTH_SHORT).show();
                }


            }
            }
        });
    }

    private void registerUser(){
        mAut.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if (task.isSuccessful()){

    Map<String, Object> map= new HashMap<>();
    map.put("name", Name);
    map.put("Email", Email);
    map.put("Password", Password);

    String id =mAut.getCurrentUser().getUid();

    mDatabase.child("Name").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete( @NonNull Task<Void> task) {

if(task.isSuccessful()){

    startActivity(new Intent(Activity_main.this, Profileactivity.class));
    finish();

}else {
    startActivity(new Intent(Activity_main.this, Profileactivity.class));
    Toast.makeText(Activity_main.this,"No se pudo completar el registro", Toast.LENGTH_SHORT).show();
finish();
}

}
    });
}

            }
        });

    }

}
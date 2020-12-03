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

class Registry_Activity extends AppCompatActivity {

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
        mDatabase= FirebaseDatabase.getInstance().getReference();;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        mEditTextName= (EditText) findViewById(R.id.editTextName);
        mEditTextEmail= (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword= (EditText) findViewById(R.id.editTextPassword);

        mButtonRegister= (Button) findViewById(R.id.btnRegister);
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
                        Toast.makeText(Registry_Activity.this, "la contraseña debe tener al menos 6 digitos", Toast.LENGTH_SHORT).show();
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

                                startActivity(new Intent(Registry_Activity.this, Profileactivity.class));
                                finish();

                            }else {
                                startActivity(new Intent(Registry_Activity.this, Profileactivity.class));
                                Toast.makeText(Registry_Activity.this,"No se pudo completar el registro", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });
                }

            }
        });

    }

}
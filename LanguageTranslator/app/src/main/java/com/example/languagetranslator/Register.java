package com.example.languagetranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView banner,registerUser;
    private EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        banner=(TextView) findViewById(R.id.banner);
//        banner.setOnClickListener(this);

        registerUser=(Button)findViewById(R.id.registerUser);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);


    }



    private void registerUser() {
        String email,password;
        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Email name is reqruied!");
            editTextEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Password name is reqruied!");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Register successful!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Register.this, "Register Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            editTextEmail.setError("Please provide valid email!");
//            editTextEmail.requestFocus();
//            return;
//        }
//        if(password.length()<6){
//            editTextPassword.setError("Password length more 6 characters!");
//            editTextPassword.requestFocus();
//            return;
//        }
    }
}
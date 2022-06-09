package com.example.languagetranslator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextEmail =findViewById(R.id.email);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });
    }

    private void sendCode() {
        String email=editTextEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Email name is reqruied!");
            editTextEmail.requestFocus();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        System.out.println(email);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this,"Email sent.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this,"Email send reset password fail.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

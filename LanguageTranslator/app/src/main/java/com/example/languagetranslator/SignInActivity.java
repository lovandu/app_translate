package com.example.languagetranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity{
    private static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "CHANNEL_1";
    private FirebaseAuth mAuth;
    private TextView banner,registerUser,tvForgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button btnSigIn;
//    private Button btnNoti;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        tvForgotPassword=findViewById(R.id.forgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSigIn=findViewById(R.id.btnsignIn);
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }


        });


    }


    private void register() {
        startActivity(new Intent(this, Register.class));
    }

    private void signIn() {
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "SignIn successful!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SignInActivity.this, "SignIn Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            if(notificationManager!=null){
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//    }
//
//    private void sendNotification() {
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.translate);
//        Notification notification=new NotificationCompat.Builder(this,SignInActivity.CHANNEL_ID)
//                .setContentTitle("Title push notification")
//                .setContentText("Message push notification")
//                .setSmallIcon(R.drawable.ic_notifications)
//                .build();
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if(notificationManager!=null){
//            notificationManager.notify(NOTIFICATION_ID,notification);
//        }
//    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.register:
//                startActivity(new Intent(this, Register.class));
//                break;
//        }
//    }
    //        btnNoti=findViewById(R.id.btnNoti);
//        createNotificationChannel();
//        btnNoti.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendNotification();
//            }
//        });

}
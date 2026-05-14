package com.example.fooddeliveryapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FourDigitCode extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourdigitcode);
        firebaseAuth = FirebaseAuth.getInstance();

        EditText editTextVerificationCode = findViewById(R.id.editText);
        Button btnVerifyCode = findViewById(R.id.btnVerifyCode);
        String userEmail = getIntent().getStringExtra("userEmail");



        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    sendEmailVerification(user);
                } else {
                    // Handle the case where user is null
                    Toast.makeText(FourDigitCode.this, "User is null.", Toast.LENGTH_SHORT).show();

                }
            }
        });
}

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FourDigitCode.this, "Verification link sent to your email", Toast.LENGTH_SHORT).show();
                            } else {
                                                    // Failed to send email
                                Toast.makeText(FourDigitCode.this, "Failed to send verification link", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                }
    }









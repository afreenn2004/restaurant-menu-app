package com.example.fooddeliveryapp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView textview1;
    private TextInputEditText usernameInput, passwordInput;
    private ImageView backarrow;
    private static final String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        mAuth = FirebaseAuth.getInstance();

        usernameInput = findViewById(R.id.name);
        passwordInput = findViewById(R.id.passwordedit);
        Button loginButton = findViewById(R.id.loginbtn);
        backarrow = findViewById(R.id.backarrow);
        textview1 = findViewById(R.id.textview1);
        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, emailVerification.class);
                startActivity(intent);
                finish();
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, ExplorePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    signInUser(email, password);
                } else {
                    Toast.makeText(loginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(loginActivity.this, "Log In Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateUI(FirebaseUser user) {
        // Implement UI update logic after successful registration if needed
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(loginActivity.this, emailVerification.class);
        startActivity(intent);
    }

    }


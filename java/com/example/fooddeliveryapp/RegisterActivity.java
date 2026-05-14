package com.example.fooddeliveryapp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout usernameInputLayout, passwordInputLayout, numberInputLayout, dobInputLayout;
    private TextInputEditText usernameEditText, passwordEditText, numberEditText, dobEditText;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        firebaseAuth = FirebaseAuth.getInstance();
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        numberInputLayout = findViewById(R.id.numberInputLayout);
        dobInputLayout = findViewById(R.id.dobInputLayout);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        numberEditText = findViewById(R.id.numberEditText);
        dobEditText = findViewById(R.id.dobEditText);
        registerBtn = findViewById(R.id.registerbtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phoneNumber = numberEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        if (email.isEmpty()){
            usernameEditText.setError("Email cannot be empty");
        }
        if(password.isEmpty()){
            passwordEditText.setError("Password cannot be empty");
        } else

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(dob)) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    updateUI(currentUser);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed." + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Registration Failed", task.getException());
                }
            }
        });
    }
    private void updateUI(FirebaseUser currentUser) {

    }
}

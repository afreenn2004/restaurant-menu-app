package com.example.fooddeliveryapp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmPass extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmpass);
        firebaseAuth = FirebaseAuth.getInstance();

        EditText editTextNewPassword = findViewById(R.id.editText);
        EditText editTextConfirmPassword = findViewById(R.id.editText1);
        Button btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(view -> {
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(ConfirmPass.this, "Please enter both passwords", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ConfirmPass.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                return;
            }
            resetPassword(newPassword);
        });
    }
    private void resetPassword(String newPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ConfirmPass.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        redirectToLoginPage();
                    } else {
                        Toast.makeText(ConfirmPass.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ConfirmPass.this, "User is null. Unable to reset password.", Toast.LENGTH_SHORT).show();
        }
    }
        private void redirectToLoginPage() {
            // Create an Intent to start the login activity (replace LoginActivity.class with your actual login activity)
            Intent intent = new Intent(getApplicationContext(), loginActivity.class);
            startActivity(intent);
            finish();
    }
        }





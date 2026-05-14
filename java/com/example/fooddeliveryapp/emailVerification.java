package com.example.fooddeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class emailVerification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emailverification);

        EditText editTextEmail = findViewById(R.id.edittext);
        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(view -> {
            String userEmail = editTextEmail.getText().toString().trim();

            if (isValidEmail(userEmail)) {
                Intent intent = new Intent(emailVerification.this, ConfirmPass.class);
                intent.putExtra("userEmail", userEmail);
                if (userEmail != null) {
                startActivity(intent);
            } else {
                    Toast.makeText(emailVerification.this, "User email is null.", Toast.LENGTH_SHORT).show();
                }
            } else {

                editTextEmail.setError("Invalid email format");
                Toast.makeText(emailVerification.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to validate email using Android's Patterns.EMAIL_ADDRESS
    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}


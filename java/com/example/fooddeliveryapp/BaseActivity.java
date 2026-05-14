package com.example.fooddeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.home) {
                            navigateToHome();
                            return true;
                        } else if (item.getItemId() == R.id.cart) {
                            navigateToCart();
                            return true;
                        }
                        return false;
                    }
                });
    }
        private void navigateToCart() {
            Log.d("Navigation", "Navigating to CartActivity");
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            finish();
        }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    }


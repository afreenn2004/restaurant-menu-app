package com.example.fooddeliveryapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Button mainCourseBtn = findViewById(R.id.mainCoursebtn);
        Button appetizerBtn = findViewById(R.id.appetizer);
        Button dessertBtn = findViewById(R.id.dessertbtn);
        Button drinksBtn = findViewById(R.id.drinks);

        mainCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, MainActivity.class));
            }
        });

        appetizerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, AppetizerActivity.class));
            }
        });
        dessertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, DessertsActivity.class));
            }
        });

        drinksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, DrinksActivity.class));
            }
        });

    }
}

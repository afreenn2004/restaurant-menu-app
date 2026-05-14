package com.example.fooddeliveryapp;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DrinksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DrinksAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview1);
        ImageView backArrow = findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d("AppetizerActivity", "onCreate");
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DrinksAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("appetizer/drinks");
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        getdata();
    }
    private void getdata(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseData", "onDataChange");
                List<MenuModel> menuList = new ArrayList<>();
                for (DataSnapshot menuSnapshot : snapshot.getChildren()) {
                    MenuModel menuModel = menuSnapshot.getValue(MenuModel.class);
                    Log.d("FirebaseData", "MenuModel: " + menuModel);
                    if (menuModel != null) {
                        int imageResourceId = getImageResourceId(DrinksActivity.this, menuModel.getimage());
                        Log.d("FirebaseData", "Image Resource ID: " + imageResourceId);
                        menuList.add(menuModel);
                    }
                }
                adapter.setData(menuList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DrinksActivity.this, "Failed to read data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}


package com.example.fooddeliveryapp;

import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class DessertsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DessertAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview2);
        ImageView backArrow = findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d("DessertsActivity", "onCreate");
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DessertAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("appetizer/dessert");
        recyclerView = findViewById(R.id.recyclerview2);
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
                    Log.d("FirebaseData", "MenuModel: " + String.valueOf(menuModel));

                    if (menuModel != null) {
                        String image = menuModel.getimage();
                        if (image != null) {
                            int imageResourceId = getImageResourceId(DessertsActivity.this, menuModel.getimage());
                            Log.d("FirebaseData", "Image Resource ID: " + imageResourceId);
                            menuList.add(menuModel);
                        }
                    }
                }
                adapter.setData(menuList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DessertsActivity.this, "Failed to read data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}


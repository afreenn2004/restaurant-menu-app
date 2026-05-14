package com.example.fooddeliveryapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainDishesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.maincourse);

        adapter = new MenuAdapter(this, new ArrayList<>());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("main_dishes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MenuModel> menuModelList = new ArrayList<>();
                for (DataSnapshot menuSnapshot : snapshot.getChildren()){
                    MenuModel menuModel = menuSnapshot.getValue(MenuModel.class);
                    if (menuModel != null){
                        menuModelList.add(menuModel);
                    }
                }
                adapter.setData(menuModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainDishesActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();

            }
        });

    }
}

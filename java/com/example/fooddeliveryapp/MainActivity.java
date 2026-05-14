package com.example.fooddeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.MenuItem;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), bottomNavCart.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    return true;
                }
                return false;
            }
        });

        ImageView backArrow = findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.d("MainActivity", "onCreate");
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MenuAdapter(this, new ArrayList<>());
        adapter.setAddToCartListener(new MenuAdapter.AddToCartListener() {
            @Override
            public void onAddToCart(MenuModel selectedItem) {
                // Handle the addition to the cart in the activity
                addToCart(selectedItem);
            }
        });
        recyclerView.setAdapter(adapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("appetizer/menu");
        recyclerView = findViewById(R.id.recyclerview);
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
                        int imageResourceId = getImageResourceId(MainActivity.this, menuModel.getimage());
                        Log.d("FirebaseData", "Image Resource ID: " + imageResourceId);
                        menuList.add(menuModel);
                    }
                }
                adapter.setData(menuList);

            }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(MainActivity.this, "Failed to read data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    });
    }
    private int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    private void addToCart(MenuModel selectedItem) {
        Log.d("Cart", "Adding item to cart: " + selectedItem.getFoodItem());
        cart.addToCart(new CartItem(selectedItem.getimage(), selectedItem.getFoodItem(), selectedItem.getPrice()));
        Log.d("Cart", "Item added to cart.");
    }
}


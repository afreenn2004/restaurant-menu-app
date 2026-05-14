package com.example.fooddeliveryapp;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;

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

public class AppetizerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppetizerAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview1);
        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, navigate to the CartActivity
                navigateToCart();
            }
        });

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

        adapter = new AppetizerAdapter(this, new ArrayList<>());
        adapter.setAddToCartListener(new AppetizerAdapter.AddToCartListener() {
            @Override
            public void onAddToCart(MenuModel selectedItem) {
                // Handle the addition to the cart in the activity
                addToCart(selectedItem);
            }
        });
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("appetizer/appetizer");
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
                        int imageResourceId = getImageResourceId(AppetizerActivity.this, menuModel.getimage());
                        Log.d("FirebaseData", "Image Resource ID: " + imageResourceId);
                        menuList.add(menuModel);
                    }
                }
                adapter.setData(menuList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppetizerActivity.this, "Failed to read data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
    private void navigateToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
    private void addToCart(MenuModel selectedItem) {
        Log.d("Cart", "Adding item to cart: " + selectedItem.getFoodItem());
        cart.addToCart(new CartItem(selectedItem.getimage(), selectedItem.getFoodItem(), selectedItem.getPrice()));
        Log.d("Cart", "Item added to cart.");
    }

}



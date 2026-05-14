package com.example.fooddeliveryapp;

import android.content.Context;
import android.util.Log;

public class CartItem {
    private String imageResource;
    private String name;
    private String price;
    private int quantity;

    public CartItem(String imageResource, String name, String price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    public int getImageResource(Context context) {
        return context.getResources().getIdentifier(imageResource, "mipmap", context.getPackageName());
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void increaseQuantity() {
        this.quantity++;
        Log.d("CartItem", "Increased quantity to: " + this.quantity);
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
            Log.d("CartItem", "Decreased quantity to: " + this.quantity);
        }
    }
}

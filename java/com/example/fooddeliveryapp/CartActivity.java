package com.example.fooddeliveryapp;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.util.List;

public class CartActivity extends BaseActivity {
    private LocationManager locationManager;
    private EditText locationEditText;
    private Cart cart;
    private static final int REQUEST_LOCATION_PERMISSION = 1; // Choose any integer value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        cart = new Cart();


        ScrollView scrollView = findViewById(R.id.scrollView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView totalTv = findViewById(R.id.totalTv);
        TextView TaxTv = findViewById(R.id.TaxTv);
        TextView discountTv = findViewById(R.id.discountTv);
        TextView totaltv = findViewById(R.id.totaltv);
        TextView textview1 = findViewById(R.id.textview1);
        EditText edittext = findViewById(R.id.edittext);
        ImageView imageview1 = findViewById(R.id.imageview1);
        ImageView imageView = findViewById(R.id.forwardarrow);
        TextView paymentTv = findViewById(R.id.paymentTv);
        RelativeLayout RL2 = findViewById(R.id.RL2);
        ImageView logo = findViewById(R.id.logo);
        Button placeOrderButton = findViewById(R.id.orderNowButton);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        List<CartItem> cartItems = cart.getCartItems();

        CartAdapter cartAdapter = new CartAdapter(cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        totalTv.setText("Sub-Total: $" + cart.getTotalPrice());
        TaxTv.setText("Tax: $" + calculateTax());
        totaltv.setText("Total: $" + calculateTotal());

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="));

                // Check if there is a map application available on the device
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    // Start the activity if there is a map application
                    startActivity(mapIntent);
                } else {
                    // Show a message or alternative action if there is no map application
                    Toast.makeText(CartActivity.this, "No map application found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private double calculateTax() {
        double taxRate = 0.1;
        return cart.getTotalPrice() * taxRate;
    }

    private double calculateTotal() {
        return cart.getTotalPriceWithTax();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop location updates when the activity is destroyed
        locationManager.removeUpdates(new MyLocationListener());
    }
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Update UI or perform other actions with the obtained location
            String locationString = "Latitude: " + latitude + ", Longitude: " + longitude;


            // Optionally, stop receiving location updates if not needed continuously
            locationManager.removeUpdates(this);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Handle status changes if needed
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Handle provider enabled
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Handle provider disabled
        }
    }
}




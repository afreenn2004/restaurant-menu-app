package com.example.fooddeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardNameEditText, cardNumberEditText, expMonthEditText, expYearEditText, cvcEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        // Initialize your EditText fields
        cardNameEditText = findViewById(R.id.cardName);
        cardNumberEditText = findViewById(R.id.cardEditText);
        expMonthEditText = findViewById(R.id.editTextExpDateMonth);
        expYearEditText = findViewById(R.id.editTextExpDateYear);
        cvcEditText = findViewById(R.id.cvcEtext);

        Button completeButton = findViewById(R.id.button);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered values
                String cardName = cardNameEditText.getText().toString();
                String cardNumber = cardNumberEditText.getText().toString();
                String expMonth = expMonthEditText.getText().toString();
                String expYear = expYearEditText.getText().toString();
                String cvc = cvcEditText.getText().toString();


                // Perform validation and payment processing here
                if (isValidInput(cardName, cardNumber, expMonth, expYear, cvc)) {
                    boolean paymentResult = PaymentService.processPayment(cardNumber, expMonth, expYear, cvc);
                    if (paymentResult) {

                        // Display a success message
                        Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                        redirectToHomePage();



                        // You can also navigate to a confirmation page or perform other actions as needed
                    } else {
                        // Display an error message for invalid input
                        Toast.makeText(PaymentActivity.this, "Invalid input. Please check your details.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void redirectToHomePage() {
        // Create an Intent to start the home activity (replace HomeActivity.class with your actual home activity)
        Intent intent = new Intent(getApplicationContext(), homePage.class);

        // Start the home activity
        startActivity(intent);

        // Finish the current activity (optional, depending on your navigation requirements)
        finish();
    }

    // Perform basic input validation
    private boolean isValidInput(String cardName, String cardNumber, String expMonth, String expYear, String cvc) {
        if (cardName.isEmpty() || cardNumber.isEmpty() || expMonth.isEmpty() || expYear.isEmpty() || cvc.isEmpty()) {
            return false;
        }

        // Validate card number
        if (!CreditCardUtils.isValid(cardNumber)) {
            // You can replace CreditCardUtils.isValid with your preferred validation method
            return false;
        }

        // Validate expiry date
        if (!isValidExpiryDate(expMonth, expYear)) {
            return false;
        }

        // Validate CVC
        if (cvc.length() != 3) {
            return false;
        }

        return true;
    }

    private boolean isValidExpiryDate(String expMonth, String expYear) {
        try {
            int month = Integer.parseInt(expMonth);
            int year = Integer.parseInt(expYear);

            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH) + 1; // January is 0

            // Check if the expiry date is in the future
            if (year > currentYear || (year == currentYear && month >= currentMonth)) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static class CreditCardUtils {

        // Validate credit card number using Luhn algorithm
        public static boolean isValid(String cardNumber) {
            int sum = 0;
            boolean alternate = false;
            for (int i = cardNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(cardNumber.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9) {
                        n = (n % 10) + 1;
                    }
                }
                sum += n;
                alternate = !alternate;
            }
            return (sum % 10 == 0);
        }
    }
    public static class PaymentService {
        public static boolean processPayment(String cardNumber, String expMonth, String expYear, String cvc) {
            // Your payment processing logic here
            return simulateRandomPaymentResult(); // Placeholder, replace with actual logic
        }
        private static boolean simulateRandomPaymentResult() {
            // Simulate a random result for payment success
            Random random = new Random();
            int randomNumber = random.nextInt(100); // Get a random number between 0 and 99

            // For demonstration purposes, let's say there's an 80% chance of success
            // Adjust the threshold as needed for your simulation
            return randomNumber < 80;
        }

    }
    }






package com.example.fooddeliveryapp;
import java.util.ArrayList;
import java.util.List;
public class Cart {
    private static List<CartItem> cartItems = new ArrayList<>();
    private static double taxRate = 0.1;
    private static double discountRate = 0.05;
    public static List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
    public static void addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public static void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public static void clearCart() {
        cartItems.clear();
    }

    public static int getCartSize() {
        return cartItems.size();
    }

    public static double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += Double.parseDouble(item.getPrice());
        }
        return totalPrice;
    }

    public static double getTotalPriceWithTax() {
        double totalPrice = getTotalPrice();
        double taxAmount = totalPrice * taxRate;
        return totalPrice + taxAmount;
    }

    public static double getTotalPriceWithDiscount() {
        double totalPrice = getTotalPrice();
        double discountAmount = totalPrice * discountRate;
        return totalPrice - discountAmount;
    }

    public static void setTaxRate(double rate) {
        taxRate = rate;
    }

    public static void setDiscountRate(double rate) {
        discountRate = rate;
    }

}

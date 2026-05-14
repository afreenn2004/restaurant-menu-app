package com.example.fooddeliveryapp;
public class MenuModel {
    private String foodItem;
    private String price;
    private String image;

    public MenuModel() {
    }

    public MenuModel(String foodItem, String price, String image) {
        this.foodItem = foodItem;
        this.price = price;
        this.image = image;
    }

    public String getFoodItem() { return foodItem;}

    public String getPrice() {
        return price;
    }

    public String getimage() {
        return image;
    }
}

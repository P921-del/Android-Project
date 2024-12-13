package com.example.food_order_application.Domains;

import android.graphics.Bitmap;

public class Item {
    private int id;
    private String Title;
    private Bitmap Picture;
    private String Description;
    private double Price;

    private int Star;
    private int Time;//The time should be taken the item is made
    private int Calories;
    private int Quantity;
    private int menuItems_id_FK;
    public Item(String title, Bitmap picture, String description, double price, int star, int time, int calories, int quantity, int menuItems_id_FK) {
        Title = title;
        Picture = picture;
        Description = description;
        Price = price;
        Star = star;
        Time = time;
        Calories = calories;
        Quantity = quantity;
        this.menuItems_id_FK = menuItems_id_FK;
    }

    public Item(int id, String title, Bitmap picture, String description, double price, int star, int time, int calories, int quantity, int menuItems_id_FK) {
        this.id = id;
        Title = title;
        Picture = picture;
        Description = description;
        Price = price;
        Star = star;
        Time = time;
        Calories = calories;
        Quantity = quantity;
        this.menuItems_id_FK = menuItems_id_FK;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Bitmap getPicture() {
        return Picture;
    }

    public void setPicture(Bitmap picture) {
        Picture = picture;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuItems_id_FK() {
        return menuItems_id_FK;
    }

    public void setMenuItems_id_FK(int menuItems_id_FK) {
        this.menuItems_id_FK = menuItems_id_FK;
    }
}

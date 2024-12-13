package com.example.food_order_application.Domains;

import android.graphics.Bitmap;

public class ItemInCart {
    private int ItemID;
    private  int OrderID;
    private String Title;
    private Bitmap Picture;
    private int Quantity;
    private double Price;
    private double TotalPriceOfItemType;
    private int CustomarID;

    public ItemInCart(int itemID, int orderID, String title, Bitmap picture, int quantity, double price, double totalPriceOfItemType, int customarID) {
        ItemID = itemID;
        OrderID = orderID;
        Title = title;
        Picture = picture;
        Quantity = quantity;
        Price = price;
        TotalPriceOfItemType = totalPriceOfItemType;
        CustomarID = customarID;
    }

    public ItemInCart(int itemID, int orderID, String title, Bitmap picture, int quantity, double price, double totalPriceOfItemType) {
        ItemID = itemID;
        OrderID = orderID;
        Title = title;
        Picture = picture;
        Quantity = quantity;
        Price = price;
        TotalPriceOfItemType = totalPriceOfItemType;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotalPriceOfItemType() {
        return TotalPriceOfItemType;
    }

    public void setTotalPriceOfItemType(double totalPriceOfItemType) {
        TotalPriceOfItemType = totalPriceOfItemType;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getCustomarID() {
        return CustomarID;
    }

    public void setCustomarID(int customarID) {
        CustomarID = customarID;
    }
}

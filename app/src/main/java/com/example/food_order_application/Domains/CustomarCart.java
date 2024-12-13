package com.example.food_order_application.Domains;

import java.util.ArrayList;

public class CustomarCart {
    double TotalPrice;
    int customarID;
    ArrayList<Integer> ItemIds = new ArrayList<Integer>();
    public CustomarCart(ArrayList<Integer> itemIds, double totalPrice) {
        ItemIds = itemIds;
        TotalPrice = totalPrice;
    }

    public ArrayList<Integer> getItemIds() {
        return ItemIds;
    }

    public void setItemIds(ArrayList<Integer> itemIds) {
        ItemIds = itemIds;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }
}

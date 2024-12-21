package com.example.food_order_application.Domains;

public class CustomarOrderHistory {
    private String CustomarName;
    private String FoodName;
    private double FoodPrice;
    private int CountOrders;
    private double TotalPriceForThisOrder;
    private String Status;
    private int ItemID;
    private int OrderID;

    public CustomarOrderHistory(String customarName, String foodName, double foodPrice, int countOrders, double totalPriceForThisOrder, String status, int itemID, int orderID) {
        CustomarName = customarName;
        FoodName = foodName;
        FoodPrice = foodPrice;
        CountOrders = countOrders;
        TotalPriceForThisOrder = totalPriceForThisOrder;
        Status = status;
        ItemID = itemID;
        OrderID = orderID;
    }

    public String getCustomarName() {
        return CustomarName;
    }

    public void setCustomarName(String customarName) {
        CustomarName = customarName;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public int getCountOrders() {
        return CountOrders;
    }

    public void setCountOrders(int countOrders) {
        CountOrders = countOrders;
    }

    public double getTotalPriceForThisOrder() {
        return TotalPriceForThisOrder;
    }

    public void setTotalPriceForThisOrder(double totalPriceForThisOrder) {
        TotalPriceForThisOrder = totalPriceForThisOrder;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        FoodPrice = foodPrice;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }
}

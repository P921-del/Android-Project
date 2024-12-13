package com.example.food_order_application.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.CartListAdapter;
import com.example.food_order_application.Adapters.ItemListAdapter;
import com.example.food_order_application.Domains.ItemInCart;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Food_Order_Application_Database db;
    ArrayList<ItemInCart> items;
    double total_items =0;
    double tax_price = 0;
    double total_pay = 0;
    private RecyclerView.Adapter cartListAdapter;
    private RecyclerView RecyclerViewCartList;
    private TextView ItemsTotals, TaxPrice, DeliveryServicesPrice, TotalPayment, emptyCart;
    private double taxPrice;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        items = db.getAllItemsInCart();
      initView();
      initRecyclyerCart(intent,getBaseContext(),items);
      calculateCart(items);
    }

    private void initRecyclyerCart(Intent intent , Context context , ArrayList<ItemInCart> items) {
        db = new Food_Order_Application_Database(context);
        RecyclerViewCartList = findViewById(R.id.RecyclerViewCartList);

        cartListAdapter = new CartListAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewCartList.setHasFixedSize(true);
        RecyclerViewCartList.setLayoutManager(linearLayoutManager);
        RecyclerViewCartList.setAdapter(cartListAdapter);
    }

    private void calculateCart(ArrayList<ItemInCart> items) {
        double percentTax = 0.02;
        double delivery = 10;
        for (int i =0; i< items.size(); i++){
            total_items += Math.round(items.get(i).getPrice()*items.get(i).getQuantity());
        }
        for (int i =0; i< items.size(); i++){
            tax_price += Math.round(items.get(i).getPrice()*items.get(i).getQuantity()*percentTax);
        }
        total_pay =  Math.round(total_items+tax_price+delivery);
        ItemsTotals.setText("$"+total_items);
        TaxPrice.setText("$"+tax_price);
        DeliveryServicesPrice.setText("$"+delivery);
        TotalPayment.setText("$"+total_pay);
    }

    private void initView() {
        ItemsTotals =findViewById(R.id.ItemsTotals);
        TaxPrice =findViewById(R.id.TaxPrice);
        DeliveryServicesPrice =findViewById(R.id.DeliveryServicesPrice);
        TotalPayment =findViewById(R.id.TotalPayment);
        emptyCart =findViewById(R.id.emptyCart);
        scrollView =findViewById(R.id.scrollView);

    }
}
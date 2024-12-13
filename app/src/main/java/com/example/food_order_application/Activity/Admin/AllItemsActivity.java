package com.example.food_order_application.Activity.Admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.ItemListAdapter;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Domains.MenuItems;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {
    RecyclerView MenuRecyclerView;
    RecyclerView.Adapter Adapter;
    Food_Order_Application_Database db = new Food_Order_Application_Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_items);
        RecyclerViewshowAllItems();
    }

    private void RecyclerViewshowAllItems() {
        MenuRecyclerView = findViewById(R.id.MenuRecyclerView);
        ArrayList<Item> items = db.getAllItems();
        Adapter = new ItemListAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        MenuRecyclerView.setHasFixedSize(true);
        MenuRecyclerView.setLayoutManager(linearLayoutManager);
        MenuRecyclerView.setAdapter(Adapter);
    }
}
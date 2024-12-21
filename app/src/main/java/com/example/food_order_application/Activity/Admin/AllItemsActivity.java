package com.example.food_order_application.Activity.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.AllItemsAdapter;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {
    ImageView BackButton;
    RecyclerView MenuRecyclerView;
    RecyclerView.Adapter Adapter;
    Food_Order_Application_Database db = new Food_Order_Application_Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_items);
        BackButton = findViewById(R.id.BackButton2);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerViewshowAllItems();
    }

    private void RecyclerViewshowAllItems() {
        MenuRecyclerView = findViewById(R.id.MenuRecyclerView);
        ArrayList<Item> items = db.getAllItems();
        Adapter = new AllItemsAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        MenuRecyclerView.setHasFixedSize(true);
        MenuRecyclerView.setLayoutManager(linearLayoutManager);
        MenuRecyclerView.setAdapter(Adapter);
    }
}
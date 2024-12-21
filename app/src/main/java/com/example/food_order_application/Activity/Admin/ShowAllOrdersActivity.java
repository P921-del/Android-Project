package com.example.food_order_application.Activity.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.ShowAllOrdersAdapter;
import com.example.food_order_application.Domains.CustomarOrderHistory;
import com.example.food_order_application.Fragments.FoodFragment;
import com.example.food_order_application.Fragments.HomeFragment;
import com.example.food_order_application.Fragments.ShowDetailsFragment;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnShowOrderDetailsClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class ShowAllOrdersActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    RecyclerView ViewManager;
    ProgressBar progressBarHistory;
    ArrayList<CustomarOrderHistory> ordersHistoryList;
    RecyclerView.Adapter ordersHistoryAdapter;
    TextView totalAmountTextView;
    ImageView back;
    Food_Order_Application_Database db ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_items);
        ViewManager = findViewById(R.id.ViewManager);
        progressBarHistory = findViewById(R.id.progressBarHistory);
        ordersHistoryList = new ArrayList<>();
        ordersHistoryAdapter = new ShowAllOrdersAdapter(ordersHistoryList, new OnShowOrderDetailsClickListener() {
            @Override
            public void OnShowDetailsClick(int ItemID) {
                FoodFragment foodFragment = FoodFragment.newInstance(ItemID);
                fragmentTransaction.replace(R.id.fragment_container2,foodFragment);
                fragmentTransaction.commit();
            }
        });
        ViewManager.setLayoutManager(new LinearLayoutManager(this));
        ViewManager.setAdapter(ordersHistoryAdapter);
        totalAmountTextView = findViewById(R.id.amount);
        back = findViewById(R.id.BackToAdminDashboardButton);

        loadOrders(getBaseContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadOrders(Context context) {
        db = new Food_Order_Application_Database(context);
        progressBarHistory.setVisibility(View.VISIBLE);
        if (db.getAllHistoryOrders() != null) {
            ordersHistoryList.clear();
            ordersHistoryList = db.getAllHistoryOrders();
            double totalAmount = db.getTotalPriceForAllOrdersInTheSystem();
            ordersHistoryAdapter.notifyDataSetChanged();
            totalAmountTextView.setText(totalAmount + " $");
            progressBarHistory.setVisibility(View.GONE);
        } else {
            Toast.makeText(context , "There are no orders yet!.",Toast.LENGTH_SHORT).show();
            progressBarHistory.setVisibility(View.GONE);
        }
    }
}
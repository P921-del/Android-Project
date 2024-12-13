package com.example.food_order_application.Activity.Admin;

import static com.example.food_order_application.R.id.addItemAdminPanel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_order_application.Activity.AccountActivities.LoginActivity;
import com.example.food_order_application.R;

public class AdminDashboardActivity extends AppCompatActivity {
    ConstraintLayout addItemAdminPanel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        addItemAdminPanel = findViewById(R.id.addItemAdminPanel);
        addItemAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddItemActivity.class));
            }
        });
    }
}
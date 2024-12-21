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
import com.example.food_order_application.Activity.AccountActivities.SignUpActivity;
import com.example.food_order_application.R;

public class AdminDashboardActivity extends AppCompatActivity {
    CardView addItemAdminPanel;
    CardView ShowAllUsersButton;
    CardView ShowAllOrdersButton , AddCustomarButton , LogoutButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        addItemAdminPanel = findViewById(R.id.addItemAdminPanel);
        ShowAllUsersButton = findViewById(R.id.ShowAllUsersButton);
        ShowAllOrdersButton = findViewById(R.id.ShowAllOrdersButton);
        AddCustomarButton = findViewById(R.id.AddCustomarButton);
        LogoutButton = findViewById(R.id.LogoutButton);
        addItemAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddItemActivity.class));
            }
        });
        ShowAllUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ShowAllUsersActivity.class));
            }
        });
        ShowAllOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ShowAllOrdersActivity.class));
            }
        });
        AddCustomarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));
            }
        });
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
    }
}
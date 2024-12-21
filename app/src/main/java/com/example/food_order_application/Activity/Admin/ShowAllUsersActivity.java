package com.example.food_order_application.Activity.Admin;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.UserAdapter;
import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnChangeClickListener;
import com.example.food_order_application.Interfaces.OnDeleteCustomarClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAllUsersActivity extends AppCompatActivity {
    private Food_Order_Application_Database db;
    private RecyclerView AllUsers;
    private ProgressBar progressBarForShowingAllUsers;

    private ArrayList<Customar> customarList;
    private UserAdapter adapter;
    private ImageView backHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_all_users);
        db = new Food_Order_Application_Database(getBaseContext());
        AllUsers = findViewById(R.id.AllUsers);
        progressBarForShowingAllUsers = findViewById(R.id.progressBarForShowingAllUsers);
        backHome = findViewById(R.id.BackHome);
        customarList = new ArrayList<>();
        adapter = new UserAdapter(customarList, new OnDeleteCustomarClickListener() {
            @Override
            public void OnDeleteCustomarClick(int position) {
                //For front
                customarList.remove(position);
                adapter.notifyItemRemoved(position);
                //For back
                Customar deletedCustomar = customarList.get(position);
                if(db.deleteCustomar(deletedCustomar)){
                    Toast.makeText(getBaseContext(),"Customar account deleted successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(),"Error in deleting account ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AllUsers.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        AllUsers.setAdapter(adapter);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getUsersFromSQLiteDatabase(db , this);
    }

    private void getUsersFromSQLiteDatabase(Food_Order_Application_Database db , Context context)
    {
        progressBarForShowingAllUsers.setVisibility(View.VISIBLE);
        customarList = db.getAllCustomars();
        if(customarList != null){
            adapter.notifyDataSetChanged();
            progressBarForShowingAllUsers.setVisibility(View.GONE);
        }
        else{
            Toast.makeText(context,"Error in fetching all customars", Toast.LENGTH_SHORT).show();
            progressBarForShowingAllUsers.setVisibility(View.GONE);
        }

    }
}
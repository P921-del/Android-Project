package com.example.food_order_application.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Adapters.CategoriesAdapter;
import com.example.food_order_application.Adapters.ItemListAdapter;
import com.example.food_order_application.Domains.MenuItems;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView ImageProfile;
    Food_Order_Application_Database db;
    TextView CustomarNameAfterLogin;
 RecyclerView CategoriesViewList,RecommenedViewList;
 RecyclerView.Adapter Adapter1,Adapter2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        CustomarNameAfterLogin = findViewById(R.id.CustomarNameAfterLogin);
        ImageProfile = findViewById(R.id.ImageProfile);
        //Sign in with google account
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getBaseContext(),googleSignInOptions);
        //If sign in with google account then retrieve the information from google on application
        GoogleSignInAccount googleAccount =  GoogleSignIn.getLastSignedInAccount(getBaseContext());
         if(googleAccount != null){
           String personName = googleAccount.getDisplayName();
             CustomarNameAfterLogin.setText("Hi, "+personName);
             ImageProfile.setImageURI(googleAccount.getPhotoUrl());
             ImageProfile.setVisibility(View.VISIBLE);
         }
         else{
             Intent intentSentFromLoginActivity = getIntent();
             String showedText = "Hi, "+intentSentFromLoginActivity.getStringExtra("CustomarName");
             CustomarNameAfterLogin.setText(showedText);
             RecyclerViewCategories();
             RecyclerViewRecommended(intentSentFromLoginActivity.getIntExtra("CustomarID",0));
         }
    }
    private void RecyclerViewCategories(){
        CategoriesViewList =findViewById(R.id.CategoriesView);
        ArrayList<MenuItems> Categories =new ArrayList<>();
        Categories.add(new MenuItems("Pizza","cat_1"));
        Categories.add(new MenuItems("Burger","cat_2"));
        Categories.add(new MenuItems("Hotdog","cat_3"));
        Categories.add(new MenuItems("Drink","cat_4"));
        Categories.add(new MenuItems("Donut","cat_5"));
        Adapter1 = new CategoriesAdapter(Categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        CategoriesViewList.setHasFixedSize(true);
        CategoriesViewList.setLayoutManager(linearLayoutManager);
        CategoriesViewList.setAdapter(Adapter1);

    }
    private void RecyclerViewRecommended(int CustomarID){
        db = new Food_Order_Application_Database(this);
        RecommenedViewList = findViewById(R.id.CategoriesView);
        ArrayList<Item> FoodList = db.getAllItems();
        //FoodList.add(new Item("Pizza1","Pepperoni pizza","slices pepperoni , mozzarella cheese, fresh oregano, ground black pepper, pizza sauce ",13.0,5,20,1000));
        //FoodList.add(new Item("burger","Cheese Burger","beef, Gouda Cheese, Special Sauce, Lettuce, tomato ",15.20,4,18,1500));
        //FoodList.add(new Item("Pizza3","Vagetable pizza"," olive oil, Vegtable oil, pitted kalamato, cherry tomatoes, fresh oregano, basil",11.0,3,16,800));
        Adapter2 = new ItemListAdapter(FoodList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        RecommenedViewList.setHasFixedSize(true);
        RecommenedViewList.setLayoutManager(linearLayoutManager);
        RecommenedViewList.setAdapter(Adapter2);
    }

}
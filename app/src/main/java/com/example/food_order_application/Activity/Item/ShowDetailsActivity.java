package com.example.food_order_application.Activity.Item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_order_application.Activity.CartActivity;
import com.example.food_order_application.ExternalFunctionsOntheProject.External;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

public class ShowDetailsActivity extends AppCompatActivity {
    Food_Order_Application_Database db;
    int count = 1;
    ImageView FoodPicture3, AddButton5, minusButton;
    TextView FoodTitle3, Quantity_RequiredForRequest, Price_Item,
            starsCount, Time_Item, calories_Item,
            TotalPriceOfItemType, addToCartButton, FoodDescription3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_details);
        Intent intentFromItemListAdapter = getIntent();
       initView(intentFromItemListAdapter,getBaseContext());
    }

    private void initView(Intent intent, Context context) {
        db = new Food_Order_Application_Database(context);
        FoodPicture3 = findViewById(R.id.FoodPicture3);
        FoodTitle3 = findViewById(R.id.FoodTitle3);
        FoodDescription3 = findViewById(R.id.FoodDescription3);
        Quantity_RequiredForRequest = findViewById(R.id.Quantity_RequiredForRequest);
        Price_Item = findViewById(R.id.Price_Item);
        starsCount = findViewById(R.id.starsCount);
        Time_Item = findViewById(R.id.Time_Item);
        calories_Item = findViewById(R.id.calories_Item);
        TotalPriceOfItemType = findViewById(R.id.TotalPriceOfItemType);
        AddButton5 = findViewById(R.id.AddButton5);
        minusButton = findViewById(R.id.minusButton);
        addToCartButton = findViewById(R.id.addToCartButton);
/*********************************************************************************************/
        FoodPicture3.setImageBitmap(External.byteArrayToBimap(intent.getByteArrayExtra("Picture")));
        FoodTitle3.setText(intent.getStringExtra("Title"));
        FoodDescription3.setText(intent.getStringExtra("Description"));
        Quantity_RequiredForRequest.setText(String.valueOf(intent.getIntExtra("Quantity",0)));
        Price_Item.setText("$"+intent.getDoubleExtra("Price",Double.MIN_VALUE));
        starsCount.setText(String.valueOf(intent.getIntExtra("Star",Integer.MIN_VALUE)));
        Time_Item.setText(String.valueOf(intent.getIntExtra("Time",Integer.MIN_VALUE)));
        calories_Item.setText(String.valueOf(intent.getIntExtra("Calories",Integer.MIN_VALUE)));
        TotalPriceOfItemType.setText("$"+count*intent.getDoubleExtra("Price",Double.MIN_VALUE));
        AddButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count +1;
                Quantity_RequiredForRequest.setText(String.valueOf(count));
                TotalPriceOfItemType.setText("$"+count*intent.getDoubleExtra("Price",Double.MIN_VALUE));
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 1)
                {
                    count = count -1;
                }
                Quantity_RequiredForRequest.setText(String.valueOf(count));
                TotalPriceOfItemType.setText("$"+count*intent.getDoubleExtra("Price",Double.MIN_VALUE));
            }
        });
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CustomarID = intent.getIntExtra("CustomarID",0);
                int ItemID = intent.getIntExtra("ItemID",0);
                double TotalAmounts = count * intent.getDoubleExtra("Price",Double.MIN_VALUE);
                if(db.createOrder(CustomarID,ItemID,TotalAmounts,count)){
                    Toast.makeText(context,"Added Successfully to the Cart",Toast.LENGTH_SHORT).show();
                    Intent intentForCartActivity = new Intent(context, CartActivity.class);
                    intentForCartActivity.putExtra("CustomarID",CustomarID);
                    context.startActivity(intentForCartActivity);
                }
                else{
                    Toast.makeText(context,"Error with the system",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
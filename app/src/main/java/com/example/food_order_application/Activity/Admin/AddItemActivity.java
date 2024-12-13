package com.example.food_order_application.Activity.Admin;
import static com.example.food_order_application.R.id.addIttem_button;
import static com.google.android.gms.cast.framework.media.ImagePicker.*;



import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_order_application.Activity.AccountActivities.LoginActivity;
import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Domains.MenuItems;
import com.example.food_order_application.Encryption_And_Decryption_Algorithm.AESCrypt;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Manifest;
import com.example.food_order_application.R;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ktx.Firebase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    Bitmap bm ;
    boolean isValid = true;
   ArrayList<MenuItems> Menues;
    ArrayAdapter<String> MenuItemsAdapter;
   AutoCompleteTextView listofMenues;
   int MenuItemID = 0;
    EditText AddFoodName,DescriptionField,PriceField,StarField,TimeField,CaloriesField,QuantityField;
    AppCompatButton addIttem_button;
    TextView selectItemImage;
    ImageView selectedItemImage;
    ActivityResultLauncher<PickVisualMediaRequest> pickVisualMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),uri ->{
        if(uri != null){
            ContentResolver CR = this.getContentResolver();
            String type = CR.getType(uri);
             bm = MediaStore.Images.Media.getBitmap(CR,uri);
            if( type.contains("image")){
                selectedItemImage.setImageBitmap(bm);
                selectedItemImage.setImageURI(uri);
                selectedItemImage.setVisibility(View.VISIBLE);
                selectedItemImage.setVisibility(View.GONE);

            }
            else if( type.contains("video")){
                Toast.makeText(this,"Please Select Only Images",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Please Select Only Images",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Please,select media",Toast.LENGTH_SHORT).show();
        }
    });


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        Menues = new ArrayList<MenuItems>();
        ArrayList<String> menuesNames = new ArrayList<>();
        menuesNames.add("Sanwiches"); menuesNames.add("Pizza");
        menuesNames.add("Drinks");
        Menues.add(new MenuItems(1,"Sandwiches","Sandwiches.png"));
        Menues.add(new MenuItems(2,"Pizza","Pizza.png"));
        Menues.add(new MenuItems(3,"Drinks","Drinks.png"));
        MenuItemsAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.activity_list_item,menuesNames);
        listofMenues = findViewById(R.id.listofMenues);
        listofMenues.setAdapter(MenuItemsAdapter);
        listofMenues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      String menuItemsName = (String) parent.getItemAtPosition(position);
                      for(int i = 0;i<Menues.size();i++){
                          if(Menues.get(i).getName().equals(menuItemsName)){
                              MenuItemID = Menues.get(i).getId();
                          }
                      }
            }
        });
        selectItemImage = findViewById(R.id.selectItemImage);
        selectedItemImage = findViewById(R.id.selectedItemImage);
        selectItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVisualMedia.launch(new PickVisualMediaRequest
                        .Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        Food_Order_Application_Database db = new Food_Order_Application_Database(this);
        AddFoodName = findViewById(R.id.AddFoodName);
        DescriptionField = findViewById(R.id.Description);
        PriceField = findViewById(R.id.Price);
        StarField = findViewById(R.id.Star);
        TimeField = findViewById(R.id.Time);
        CaloriesField = findViewById(R.id.Calories);
        QuantityField = findViewById(R.id.Quantity);

        DescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the foodName field",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        PriceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("") || DescriptionField.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        StarField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("") || DescriptionField.getText().toString().trim().equals("") || PriceField.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        TimeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("") || DescriptionField.getText().toString().trim().equals("") || PriceField.getText().toString().trim().equals("")|| StarField.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        CaloriesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("") || DescriptionField.getText().toString().trim().equals("") || PriceField.getText().toString().trim().equals("")|| StarField.getText().toString().trim().equals("") || TimeField.getText().toString().trim().equals("") ){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        QuantityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(AddFoodName.getText().toString().trim().equals("") || DescriptionField.getText().toString().trim().equals("") || PriceField.getText().toString().trim().equals("")|| StarField.getText().toString().trim().equals("") || TimeField.getText().toString().trim().equals("")|| CaloriesField.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addIttem_button = (AppCompatButton)findViewById(R.id.addIttem_button);
        addIttem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValid && bm==null && MenuItemID == 0 && AddFoodName.getText().toString().equals("") && DescriptionField.getText().toString().equals("") && PriceField.getText().toString().equals("") && StarField.getText().toString().equals("") && TimeField.getText().toString().equals("") && CaloriesField.getText().toString().equals("") && QuantityField.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Please,All fields are required!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String Title = AddFoodName.getText().toString().trim();
                    String Description = DescriptionField.getText().toString().trim();
                    int Price =Integer.parseInt(PriceField.getText().toString().trim());
                    int Star = Integer.parseInt(StarField.getText().toString().trim());
                    int Time = Integer.parseInt(TimeField.getText().toString().trim());
                    int Calories = Integer.parseInt(CaloriesField.getText().toString().trim());
                    int Quantity = Integer.parseInt(QuantityField.getText().toString().trim());
                    Item item = new Item(Title,bm,Description,Price,Star,Time,Calories,Quantity,MenuItemID);
                    db.addItem(item);
                    Toast.makeText(getBaseContext(),"Item Added Successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }

            }
        });
    }
}
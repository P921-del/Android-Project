package com.example.food_order_application.Activity.AccountActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_order_application.Activity.AccountActivities.LoginActivity;
import com.example.food_order_application.Activity.CartActivity;
import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Encryption_And_Decryption_Algorithm.AESCrypt;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;
import com.google.android.material.internal.TextWatcherAdapter;

import java.security.GeneralSecurityException;

public class SignUpActivity extends AppCompatActivity {
    TextView alreadyHaveAccount;
    EditText customarName,Address,EmailSignUp,ContactNumber,passwordSignUP,confirmPasswordSignUP;
    boolean isValid = true;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        Food_Order_Application_Database db = new Food_Order_Application_Database(this);
        customarName = findViewById(R.id.customarName);
        Address = findViewById(R.id.Address);
        EmailSignUp = findViewById(R.id.EmailSignUp);
        ContactNumber = findViewById(R.id.ContactNumber);
        passwordSignUP = findViewById(R.id.passwordSignUP);
        confirmPasswordSignUP = findViewById(R.id.confirmPasswordSignUP);
        customarName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if(!db.CheckCustomarNameUniqueForCreate(String.valueOf(s))){
                 isValid =false;
                 Toast.makeText(getBaseContext(),"Please,Choose another name this name is used with another user",Toast.LENGTH_SHORT).show();
            }
        }
    });
      Address.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              if(customarName.getText().toString().trim().equals("")){
                  isValid = false;
                  Toast.makeText(getBaseContext(),"Please,Fill the Name field",Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });
        ContactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(customarName.getText().toString().trim().equals("") || Address.getText().toString().trim().equals("")){
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
        EmailSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(customarName.getText().toString().trim().equals("") || Address.getText().toString().trim().equals("") || ContactNumber.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!ValidateEmailAddress(String.valueOf(s))){
                    isValid=false;
                    Toast.makeText(getBaseContext(),"Please,Write a valid emailAddress",Toast.LENGTH_SHORT).show();
                }
                if(!db.CheckCustomarEmailUniqueForCreate(String.valueOf(s))){
                    isValid=false;
                    Toast.makeText(getBaseContext(),"Please,Choose another email this email is used with another user",Toast.LENGTH_SHORT).show();
                }
            }
        });
        passwordSignUP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(customarName.getText().toString().trim().equals("") || Address.getText().toString().trim().equals("") || ContactNumber.getText().toString().trim().equals("") || EmailSignUp.getText().toString().trim().equals("")){
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
        confirmPasswordSignUP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(customarName.getText().toString().trim().equals("") || Address.getText().toString().trim().equals("") || ContactNumber.getText().toString().trim().equals("") || EmailSignUp.getText().toString().trim().equals("") || passwordSignUP.getText().toString().trim().equals("")){
                    isValid = false;
                    Toast.makeText(getBaseContext(),"Please,Fill the previous fields",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(!confirmPasswordSignUP.getText().toString().trim().equals(passwordSignUP.getText().toString().trim())){
                   isValid =false;
                   Toast.makeText(getBaseContext(),"Confirm Password must matches the Password field!",Toast.LENGTH_SHORT).show();
               }
            }
        });
        alreadyHaveAccount = findViewById(R.id.donotHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValid && customarName.getText().toString().equals("") && Address.getText().toString().equals("") && ContactNumber.getText().toString().equals("") && EmailSignUp.getText().toString().equals("") && passwordSignUP.getText().toString().equals("") && confirmPasswordSignUP.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Please,All fields are required!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String Name = customarName.getText().toString().trim();
                    String customarAddress = Address.getText().toString().trim();
                    int customarContactNumber =Integer.parseInt(ContactNumber.getText().toString().trim());
                    String Email = EmailSignUp.getText().toString();
                    String decryptedPassword = passwordSignUP.getText().toString().trim();
                    String Password ="";
                    try {
                        Password = AESCrypt.encrypt(decryptedPassword);
                    }catch (Exception e){
                        //handle error
                        Toast.makeText(getBaseContext(),"Error in the system ,Please wait until fixed!",Toast.LENGTH_LONG).show();

                    }
         // trim function doesnot use in some casess here
                    //Encrypt the password here
                    Customar customar = new Customar(Name,customarAddress,customarContactNumber,Email,Password);
                    if(db.createCustomar(customar)){
                        Toast.makeText(getBaseContext(),"SignUp Successfully ,Congratulations!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Error with the system",Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }
    public boolean ValidateEmailAddress(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        else {
            return false;
        }
    }
}
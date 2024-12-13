package com.example.food_order_application.Activity.AccountActivities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food_order_application.Activity.Admin.AdminDashboardActivity;
import com.example.food_order_application.Activity.MainActivity;
import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    AppCompatButton GoogleSignInButton;
    Food_Order_Application_Database db;
    EditText customarEmailforLogin;
    EditText customarPasswordforLogin;
    TextView donotHaveAccount;
    AppCompatButton loginbutton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        //Sign in with google account
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getBaseContext(),googleSignInOptions);
        navigateUsingGoogleAccountToSecondActivity();
        GoogleSignInButton = findViewById(R.id.GoogleSignInButton);
        GoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInWithGoogleAccountIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInWithGoogleAccountIntent,1000);
            }
        });
        db = new Food_Order_Application_Database(this);
        customarEmailforLogin = findViewById(R.id.customarEmailforLogin);
        customarPasswordforLogin = findViewById(R.id.customarPasswordforLogin);
        donotHaveAccount = findViewById(R.id.donotHaveAccount);
        loginbutton = findViewById(R.id.loginbutton);
        donotHaveAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getBaseContext(), SignUpActivity.class));
           }
       });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customarEmailforLogin.getText().toString().trim().equals("admin") && customarPasswordforLogin.getText().toString().trim().equals("salem123") && db.checkCredientials(customarEmailforLogin.getText().toString().trim(),customarPasswordforLogin.getText().toString().trim(),getBaseContext()) == null){
                    startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                }
                else if (db.checkCredientials(customarEmailforLogin.getText().toString().trim(),customarPasswordforLogin.getText().toString().trim(),getBaseContext()) != null) {
                    Customar customar = db.checkCredientials(customarEmailforLogin.getText().toString().trim(),customarPasswordforLogin.getText().toString().trim(),getBaseContext());
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    intent.putExtra("CustomarID",customar.getCustomarID());
                    intent.putExtra("CustomarName",customar.getName());
                    intent.putExtra("CustomarEmail",customar.getEmail());
                    intent.putExtra("CustoamrAddress",customar.getAddress());
                    intent.putExtra("CustomarContactNumber",customar.getContactNumber());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateUsingGoogleAccountToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getBaseContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateUsingGoogleAccountToSecondActivity() {
        finish();
        GoogleSignInAccount googleAccount =  GoogleSignIn.getLastSignedInAccount(getBaseContext());
        if(googleAccount != null){
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            intent.putExtra("CustomarID",Integer.parseInt(googleAccount.getId()));
            intent.putExtra("CustomarName",googleAccount.getDisplayName());
            intent.putExtra("CustomarEmail",googleAccount.getEmail());
            intent.putExtra("CustoamrAddress","No Address");
            intent.putExtra("CustomarContactNumber",000333777555);
            startActivity(intent);
        }
    }
}
package com.example.food_order_application.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.food_order_application.Adapters.TabLayoutAdapters.FirstPagerAdapter;
import com.example.food_order_application.Domains.TabLayoutDomains.TabInsideMainActivity;
import com.example.food_order_application.Fragments.CartFragment;
import com.example.food_order_application.Fragments.HomeFragment;
import com.example.food_order_application.Fragments.ProfileFragment;
import com.example.food_order_application.Fragments.ShowDetailsFragment;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentListener , ShowDetailsFragment.OnShowDetailsFragmentListener , CartFragment.OnPayClickListener {

    double storedPaymentInDatabase = 0.00;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    TabLayout tabLayout1;
    ViewPager2 ViewPager1;
    ArrayList<TabInsideMainActivity> tabs;
    FirstPagerAdapter ViewPagerFirstAdapter;

    Intent intentSentFromLoginActivity = new Intent();
    int CustomarID = intentSentFromLoginActivity.getIntExtra("CustomarID",0);
    String CustomarName = intentSentFromLoginActivity.getStringExtra("CustomarName");


    private String clientID = "AZskezdLdX4IUoeTE5ssEO23VJ5hJGkVYfc3jEMGVBsq8NADXkI0Jb81Za9p__R5gshJly14TwXSi79Q";
    private int PAYPALL_REQUEST_CODE = 123;

    private PayPalConfiguration configuration;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tabLayout1 = findViewById(R.id.tabLayout1);
        ViewPager1 = findViewById(R.id.ViewPager1);
        tabs = new ArrayList<>();

        //Sign in with google account
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getBaseContext(),googleSignInOptions);
        //If sign in with google account then retrieve the information from google on application
        GoogleSignInAccount googleAccount =  GoogleSignIn.getLastSignedInAccount(getBaseContext());
         if(googleAccount != null){
           String personName = googleAccount.getDisplayName();
             //CustomarNameAfterLogin.setText("Hi, "+personName);
             //ImageProfile.setImageURI(googleAccount.getPhotoUrl());
             //ImageProfile.setVisibility(View.VISIBLE);
         }
         else{
             /* The modified code here*/
             tabs.add(new TabInsideMainActivity("Home", R.drawable.home , HomeFragment.newInstance(CustomarID,CustomarName) ));
             tabs.add(new TabInsideMainActivity("Cart", R.drawable.shopping_cart , CartFragment.newInstance(CustomarID) ));
             tabs.add(new TabInsideMainActivity("Profile", R.drawable.user , ProfileFragment.newInstance(CustomarID) ));
             ViewPagerFirstAdapter = new FirstPagerAdapter(getSupportFragmentManager() , getLifecycle() , tabs);
             ViewPager1.setAdapter( ViewPagerFirstAdapter );
             new TabLayoutMediator(tabLayout1, ViewPager1, new TabLayoutMediator.TabConfigurationStrategy() {
                 @Override
                 public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                     tabs.get(position).setTabIndex(position);
                     tab.setText(tabs.get(position).getTabName());
                     tab.setIcon(tabs.get(position).getTabIcon());
                 }
             }).attach();
             /* The modified code here*/
         }
        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientID);
    }


    @Override
    public void OnHomeFragmentIntegration(int ItemID) {
        ShowDetailsFragment showDetailsFragment = ShowDetailsFragment.newInstance(ItemID , CustomarID);
        showDetailsFragment.show(getSupportFragmentManager(),null);
    }

    @Override
    public void OnShowDetailsFragmentIntegration() {
        int searchIndex = 0;
        for(int i =0;i<tabs.size();i++){
            if(tabs.get(i).getTabName().equals("Cart")){
                searchIndex = i;
            }
        }
        Objects.requireNonNull(tabLayout1.getTabAt(searchIndex)).select();

    }

    @Override
    public void OnPayClick(double Total_pay) {
        getPayment(Total_pay);
    }
    private void getPayment( double Total_pay) {
        storedPaymentInDatabase = Total_pay;
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(Total_pay) , "USA" ,
                "Code With SE Team in FCI" , PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getBaseContext() , PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT , payment);

        startActivityForResult( intent , PAYPALL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Food_Order_Application_Database db = new Food_Order_Application_Database(this);

        if(requestCode == PAYPALL_REQUEST_CODE )
        {
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if ( paymentConfirmation != null)
            {
                try
                {
                    String paymentDetails = paymentConfirmation.toJSONObject().toString();
                    if (db.PayByPayPal(storedPaymentInDatabase , CustomarID) )
                    {
                        Toast.makeText(this , "SuccessFully Payment" ,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this ,"Failed Payment" ,Toast.LENGTH_SHORT).show();
                    }
                    JSONObject object = new JSONObject(paymentDetails);
                }
                catch (JSONException e)
                {
                    Toast.makeText(this , e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if ( requestCode == Activity.RESULT_CANCELED)
        {
            Toast.makeText(this , "Error wiht paypal method" ,Toast.LENGTH_SHORT).show();
        }
        else if ( requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(this , "Invalid Paypal payment" ,Toast.LENGTH_SHORT).show();
        }
    }
}
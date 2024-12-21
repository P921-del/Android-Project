package com.example.food_order_application.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application.Adapters.CartListAdapter;
import com.example.food_order_application.Domains.ItemInCart;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnChangeClickListener;
import com.example.food_order_application.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int CustomarID;
    private String mParam2;

   private Food_Order_Application_Database db;
   private ArrayList<ItemInCart> items;
   private double total_items =0;
   private double tax_price = 0;
   private double total_pay = 0;
    private RecyclerView.Adapter cartListAdapter;
    private RecyclerView RecyclerViewCartList;
    private TextView ItemsTotals, TaxPrice, DeliveryServicesPrice, TotalPayment, emptyCart;
    private ConstraintLayout CheckOutButton;
    private double taxPrice;
    private ScrollView scrollView;


   private OnPayClickListener Listener;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(int param1) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CustomarID = getArguments().getInt(ARG_PARAM1);
        }
        items = db.getAllItemsInCart(CustomarID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        initRecyclyerCart(view , getActivity() , items);
        calculateCart(items);
        return view;
    }

    private void initView(View view) {
        ItemsTotals = view.findViewById(R.id.ItemsTotals);
        TaxPrice = view.findViewById(R.id.TaxPrice);
        DeliveryServicesPrice = view.findViewById(R.id.DeliveryServicesPrice);
        TotalPayment = view.findViewById(R.id.TotalPayment);
        emptyCart = view.findViewById(R.id.emptyCart);
        CheckOutButton = view.findViewById(R.id.CheckOutButton);
        scrollView = view.findViewById(R.id.scrollView);
    }

    private void initRecyclyerCart(View view , Context context , ArrayList<ItemInCart> items) {
        db = new Food_Order_Application_Database(context);
        RecyclerViewCartList = view.findViewById(R.id.RecyclerViewCartList);

        cartListAdapter = new CartListAdapter(items, new OnChangeClickListener() {
            @Override
            public void OnItemChange(int OrderID, int count, double total) {
                db = new Food_Order_Application_Database(getActivity());
                if(db.changeQuantityOfOrderByID(OrderID,count,total)){
                    Toast.makeText(getActivity(), "Updates Succes!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Error in the system,Updates information doesn't occur!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewCartList.setHasFixedSize(true);
        RecyclerViewCartList.setLayoutManager(linearLayoutManager);
        RecyclerViewCartList.setAdapter(cartListAdapter);
    }

    private void calculateCart(ArrayList<ItemInCart> items) {
        double percentTax = 0.02;
        double delivery = 10;
        for (int i =0; i< items.size(); i++){
            total_items += Math.round(items.get(i).getPrice()*items.get(i).getQuantity());
        }
        for (int i =0; i< items.size(); i++){
            tax_price += Math.round(items.get(i).getPrice()*items.get(i).getQuantity()*percentTax);
        }
        total_pay =  Math.round(total_items+tax_price+delivery);
        ItemsTotals.setText("$"+total_items);
        TaxPrice.setText("$"+tax_price);
        DeliveryServicesPrice.setText("$"+delivery);
        TotalPayment.setText("$"+total_pay);
        CheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listener.OnPayClick(total_pay);
            }
        });
    }

    public interface OnPayClickListener{
        void OnPayClick(double Total_pay);
    }
}
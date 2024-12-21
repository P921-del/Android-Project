package com.example.food_order_application.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int ItemID;
    private Food_Order_Application_Database db;
    ImageView ImageForFoodFragment;
    TextView TitleForFoodFragment , TimeForFoodFragment , PriceForFoodFragment , StarsForFoodFragment;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ItemID Parameter 1.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(int ItemID) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, ItemID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ItemID = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ImageForFoodFragment = view.findViewById(R.id.ImageForFoodFragment);
        TitleForFoodFragment = view.findViewById(R.id.TitleForFoodFragment);
        TimeForFoodFragment = view.findViewById(R.id.TimeForFoodFragment);
        PriceForFoodFragment = view.findViewById(R.id.PriceForFoodFragment);
        StarsForFoodFragment = view.findViewById(R.id.StarsForFoodFragment);
        // load Customar Data on the fragment
        loadUserData();
        return view;
    }

    private void loadUserData() {
        db = new Food_Order_Application_Database(getActivity());
        Item currentItem = db.getItemByID(ItemID);
        if (currentItem != null) {
            if (currentItem.getPicture() != null) {
                ImageForFoodFragment.setImageBitmap(currentItem.getPicture());
            }

            if (currentItem.getTitle() != null) {
                TitleForFoodFragment.setText(currentItem.getTitle());
            }

            if (currentItem.getTime() != 0) {
                TimeForFoodFragment.setText(String.valueOf( currentItem.getTime() ));
            }
            if (currentItem.getPrice() != 0.00) {
                PriceForFoodFragment.setText(currentItem.getPrice()+" $");
            }
            if (currentItem.getStar() != 0) {
                TimeForFoodFragment.setText(String.valueOf( currentItem.getStar() ));
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Item data not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.food_order_application.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowDetailsFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int CustomarID;
    private int ItemID;

   private Food_Order_Application_Database db;
   private int count = 1;
   private ImageView FoodPicture3, AddButton5, minusButton , CancelFromShowDetails;
   private TextView FoodTitle3, Quantity_RequiredForRequest, Price_Item,
            starsCount, Time_Item, calories_Item,
            TotalPriceOfItemType, addToCartButton, FoodDescription3;
    private OnShowDetailsFragmentListener Listener;

    public ShowDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowDetailsFragment newInstance(int param1, int param2) {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if ( context instanceof OnShowDetailsFragmentListener){
            Listener = (OnShowDetailsFragmentListener) context;
        }
        else{
            throw new ClassCastException("The Main Activity doesnot implement OnHomeFragementListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CustomarID = getArguments().getInt(ARG_PARAM1);
            ItemID = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_details, container, false);
        initView(v , getActivity());
        return v;
    }

    private void initView(View view, Context context) {
        db = new Food_Order_Application_Database(context);
        FoodPicture3 = view.findViewById(R.id.FoodPicture3);
        FoodTitle3 = view.findViewById(R.id.FoodTitle3);
        FoodDescription3 = view.findViewById(R.id.FoodDescription3);
        Quantity_RequiredForRequest = view.findViewById(R.id.Quantity_RequiredForRequest);
        Price_Item = view.findViewById(R.id.Price_Item);
        starsCount = view.findViewById(R.id.starsCount);
        Time_Item = view.findViewById(R.id.Time_Item);
        calories_Item = view.findViewById(R.id.calories_Item);
        TotalPriceOfItemType = view.findViewById(R.id.TotalPriceOfItemType);
        CancelFromShowDetails = view.findViewById(R.id.CancelFromShowDetails);
        AddButton5 = view.findViewById(R.id.AddButton5);
        minusButton = view.findViewById(R.id.minusButton);
        addToCartButton = view.findViewById(R.id.addToCartButton);
/*********************************************************************************************/
        if(db.getItemByID(ItemID) != null){
            Item item = db.getItemByID(ItemID);
            FoodPicture3.setImageBitmap(item.getPicture());
            FoodTitle3.setText(item.getTitle());
            FoodDescription3.setText(item.getDescription());
            Quantity_RequiredForRequest.setText(item.getQuantity());
            Price_Item.setText("$"+item.getPrice());
            starsCount.setText(String.valueOf(item.getStar()));
            Time_Item.setText(String.valueOf(item.getTime()));
            calories_Item.setText(String.valueOf(item.getCalories()));
            TotalPriceOfItemType.setText("$"+count * item.getPrice());

            CancelFromShowDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            AddButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = count +1;
                    Quantity_RequiredForRequest.setText(String.valueOf(count));
                    TotalPriceOfItemType.setText("$"+count * item.getPrice());
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
                    TotalPriceOfItemType.setText("$"+count * item.getPrice());
                }
            });
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double TotalAmounts = count * item.getPrice();
                    if(db.createOrder(CustomarID,ItemID,TotalAmounts,count)){
                        Toast.makeText(context,"Added Successfully to the Cart",Toast.LENGTH_SHORT).show();
                        dismiss();
                        Listener.OnShowDetailsFragmentIntegration();
                    }
                    else{
                        Toast.makeText(context,"Error with the system",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    public interface OnShowDetailsFragmentListener{
        void OnShowDetailsFragmentIntegration();
    }
}
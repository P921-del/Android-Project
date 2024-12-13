package com.example.food_order_application.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Domains.ItemInCart;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {
    Food_Order_Application_Database db;
    private  int count = 0;

    ArrayList<ItemInCart> FoodList;
    int CustomarID;

    public CartListAdapter(ArrayList<ItemInCart> FoodList) {
        this.FoodList = FoodList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_recommended,null,false);
        return new CartViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return FoodList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, @SuppressLint("RecyclerView") int position) {
        cartViewHolder.FoodPicture.setImageBitmap(FoodList.get(position).getPicture());
        cartViewHolder.FoodName.setText(FoodList.get(position).getTitle());
        cartViewHolder.FoodPrice.setText("$"+FoodList.get(position).getPrice());
        cartViewHolder.Quantity_RequiredForRequest.setText(""+FoodList.get(position).getQuantity());
        cartViewHolder.TotalPriceForFood.setText("$"+Math.round(FoodList.get(position).getPrice() * FoodList.get(position).getQuantity()));
        count = FoodList.get(position).getQuantity();
        //Button Handllers
        cartViewHolder.AddButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert in the database and retrieve it from database
                count =count +1;
                double total = Math.round(FoodList.get(position).getPrice() * count);
                db = new Food_Order_Application_Database(cartViewHolder.AddButton5.getContext());
                if(db.changeQuantityOfOrderByID(FoodList.get(position).getOrderID(),count,total)){
                    cartViewHolder.Quantity_RequiredForRequest.setText(""+count);
                    cartViewHolder.TotalPriceForFood.setText("$"+total);
                    FoodList.get(position).setQuantity(count);
                    FoodList.get(position).setTotalPriceOfItemType(total);
                    notifyItemChanged(position);
                }
                else{
                    Toast.makeText(cartViewHolder.AddButton5.getContext(), "Error in the system,Updates information doesn't occur!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cartViewHolder.minusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Insert in the database and retrieve it from database
                if(count > 1){
                    count =count -1;
                    double total = Math.round(FoodList.get(position).getPrice() * count);
                    db = new Food_Order_Application_Database(cartViewHolder.AddButton5.getContext());
                    if(db.changeQuantityOfOrderByID(FoodList.get(position).getOrderID(),count,total)){
                        cartViewHolder.Quantity_RequiredForRequest.setText(""+count);
                        cartViewHolder.TotalPriceForFood.setText("$"+total);
                        FoodList.get(position).setQuantity(count);
                        FoodList.get(position).setTotalPriceOfItemType(total);
                        notifyItemChanged(position);
                    }

                }

            }
            }
        });
    }

     class CartViewHolder extends RecyclerView.ViewHolder{
       ImageView FoodPicture , AddButton5, minusButton;
       TextView FoodName, FoodPrice, TotalPriceForFood, Quantity_RequiredForRequest;
       ConstraintLayout CartLayout;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            FoodPicture = itemView.findViewById(R.id.FoodPicture);
            Quantity_RequiredForRequest = itemView.findViewById(R.id.Quantity_RequiredForRequest);
            AddButton5 = itemView.findViewById(R.id.AddButton5);
            minusButton = itemView.findViewById(R.id.minusButton);
            FoodName = itemView.findViewById(R.id.FoodName);
            FoodPrice = itemView.findViewById(R.id.FoodPrice);
            TotalPriceForFood = itemView.findViewById(R.id.TotalPriceForFood);
            CartLayout = itemView.findViewById(R.id.CartLayout);
        }
    }
}

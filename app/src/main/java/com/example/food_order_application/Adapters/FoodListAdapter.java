package com.example.food_order_application.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnItemClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    private Food_Order_Application_Database db;
    int ItemID;
    public FoodListAdapter(int itemID) {
       this.ItemID = itemID;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_recommended,null,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder FoodViewHolder, @SuppressLint("RecyclerView") int i) {
        db = new Food_Order_Application_Database(FoodViewHolder.itemView.getContext());
        Item item = db.getItemByID(ItemID);
        FoodViewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView Picture;
        TextView Title,Price;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            Picture = itemView.findViewById(R.id.Picture);
            Title = itemView.findViewById(R.id.FoodName);
            Price = itemView.findViewById(R.id.Price);
           // FoodLayout = itemView.findViewById(R.id.FoodLayout);
        }

        public void bind(Item item) {
           /* FoodViewHolder.Picture.setImageBitmap(ItemList.get(i).getPicture());
            FoodViewHolder.Title.setText(ItemList.get(i).getTitle());
            FoodViewHolder.Price.setText(String.valueOf(ItemList.get(i).getPrice()));*/
        }
    }
}

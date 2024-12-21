package com.example.food_order_application.Adapters;

import static java.nio.file.Files.delete;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.addItemviewholder>{
    ArrayList<Item> ItemList;
    public AllItemsAdapter(ArrayList<Item> itemList){
     this.ItemList =itemList;
    }
    @NonNull
    @Override
    public addItemviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item,null,false);
        return new addItemviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addItemviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.FoodPicture2.setImageBitmap(ItemList.get(position).getPicture());
        holder.FoodTitle2.setText(ItemList.get(position).getTitle());
        holder.FoodPrice2.setText(String.valueOf(ItemList.get(position).getPrice()));
        holder.FoodQuantity2.setText(String.valueOf(ItemList.get(position).getQuantity()));
        holder.addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity(position);
            }

            private void increaseQuantity(int position) {
                int oldQuantity = ItemList.get(position).getQuantity();
                if(oldQuantity<10) {
                    ItemList.get(position).setQuantity(oldQuantity+1);
                    notifyItemChanged(position);
                    holder.FoodQuantity2.setText(String.valueOf(ItemList.get(position).getQuantity()));
                }
            }

        });
        holder.minusButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity(position);
            }

            private void decreaseQuantity(int position) {
                int oldQuantity = ItemList.get(position).getQuantity();
                if(oldQuantity > 1) {
                    ItemList.get(position).setQuantity(oldQuantity-1);
                    notifyItemChanged(position);
                    holder.FoodQuantity2.setText(String.valueOf(ItemList.get(position).getQuantity()));
                }
            }
        });
        holder.deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuantity(position);
            }

            private void deleteQuantity(int position) {
                ItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,ItemList.size());
            }
        });
    }
    @Override
    public int getItemCount() {
        return ItemList.size();
    }
    class addItemviewholder extends RecyclerView.ViewHolder{
        ImageView FoodPicture2,addButton2,minusButton2,deleteButton2;
        TextView FoodTitle2,FoodPrice2,FoodQuantity2;
        ConstraintLayout viewholder_item_layout;
        public addItemviewholder(@NonNull View itemView) {
            super(itemView);
            FoodPicture2 = itemView.findViewById(R.id.FoodPicture2);
            addButton2 = itemView.findViewById(R.id.addButton2);
            minusButton2 = itemView.findViewById(R.id.minusButton2);
            deleteButton2 = itemView.findViewById(R.id.deleteButton2);
            FoodTitle2 = itemView.findViewById(R.id.FoodTitle2);
            FoodQuantity2 = itemView.findViewById(R.id.FoodQuantity2);
            FoodPrice2 = itemView.findViewById(R.id.FoodPrice2);
            viewholder_item_layout = itemView.findViewById(R.id.viewholder_item_layout);

        }
    }
}

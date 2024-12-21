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
import com.example.food_order_application.Interfaces.OnItemClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    byte[] imageInBytes;
    ArrayList<Item> ItemList;
    int CustomarID;
    OnItemClickListener Listener;
    int drawbleResourceIdForItemPicture;
    public ItemListAdapter(ArrayList<Item> ItemList , OnItemClickListener listener) {
        this.ItemList = ItemList;
        this.CustomarID = CustomarID;
        this.Listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_recommended,null,false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder ItemViewHolder, @SuppressLint("RecyclerView") int i) {
        ItemViewHolder.Picture.setImageBitmap(ItemList.get(i).getPicture());
        ItemViewHolder.Title.setText(ItemList.get(i).getTitle());
        ItemViewHolder.Price.setText(String.valueOf(ItemList.get(i).getPrice()));
        ItemViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intentForItemShowDetails = new Intent(ItemViewHolder.addButton.getContext(), ShowDetailsActivity.class);
                intentForItemShowDetails.putExtra("CustoamrID",CustomarID);
                intentForItemShowDetails.putExtra("Title",ItemList.get(i).getTitle());
                intentForItemShowDetails.putExtra("Description",ItemList.get(i).getDescription());
                intentForItemShowDetails.putExtra("ItemID",ItemList.get(i).getId());
                intentForItemShowDetails.putExtra("Star",ItemList.get(i).getStar());
                intentForItemShowDetails.putExtra("Time",ItemList.get(i).getTime());
                intentForItemShowDetails.putExtra("Calories",ItemList.get(i).getCalories());
                ItemList.get(i).setQuantity(1);
                intentForItemShowDetails.putExtra("Quantity",ItemList.get(i).getQuantity());
                intentForItemShowDetails.putExtra("Price",ItemList.get(i).getPrice());*/
                //how to save image inside android with java project
                /*ByteArrayOutputStream objectByteOutputStream = new ByteArrayOutputStream();
                ItemList.get(i).getPicture().compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
                imageInBytes = objectByteOutputStream.toByteArray();
                intentForItemShowDetails.putExtra("Picture",imageInBytes);
                ItemViewHolder.itemView.getContext().startActivity(intentForItemShowDetails);*/
                Listener.OnItemClick(ItemList.get(i).getId());

            }
        });
    }

     class ItemViewHolder extends RecyclerView.ViewHolder{
       ImageView Picture,addButton;
       TextView Title,Price;
       ConstraintLayout RecommendedLayout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Picture = itemView.findViewById(R.id.Picture);
            Title = itemView.findViewById(R.id.FoodName);
            Price = itemView.findViewById(R.id.Price);
            addButton = itemView.findViewById(R.id.addButton);
            RecommendedLayout = itemView.findViewById(R.id.RecommendedLayout);
        }
    }
}

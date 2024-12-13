package com.example.food_order_application.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_order_application.Domains.MenuItems;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    ArrayList<MenuItems> Categories;
    String PictureURL;
    int drawbleResourceId;
    public CategoriesAdapter(ArrayList<MenuItems> categories) {
        Categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_category,null,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        MenuItems category = Categories.get(i);
        drawbleResourceId = categoryViewHolder.itemView.getContext().getResources()
                        .getIdentifier(category.getCategoryImage(),"drawable",categoryViewHolder.itemView.getContext().getPackageName());
        categoryViewHolder.categoryImage.setImageResource(drawbleResourceId);
        categoryViewHolder.categoryName.setText(category.getName());
    }

     class CategoryViewHolder extends RecyclerView.ViewHolder{
       ImageView categoryImage;
       TextView categoryName;
       ConstraintLayout CategoryLayout;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            CategoryLayout = itemView.findViewById(R.id.CategoryLayout);
        }
    }
}

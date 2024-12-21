package com.example.food_order_application.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Domains.CustomarOrderHistory;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnShowOrderDetailsClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class ShowAllOrdersAdapter extends RecyclerView.Adapter<ShowAllOrdersAdapter.ShowAllOrdersViewHolder> {
    ArrayList<CustomarOrderHistory> CustomarOrdersHistory;
    OnShowOrderDetailsClickListener listener;
    public ShowAllOrdersAdapter(ArrayList<CustomarOrderHistory> customarOrdersHistory , OnShowOrderDetailsClickListener listener){
        this.CustomarOrdersHistory = customarOrdersHistory;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ShowAllOrdersAdapter.ShowAllOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_show_all_orders , parent , false);
        return new ShowAllOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllOrdersAdapter.ShowAllOrdersViewHolder holder, int position) {
           CustomarOrderHistory orderDetails = CustomarOrdersHistory.get(position);
          holder.bind( orderDetails , position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ShowAllOrdersViewHolder extends RecyclerView.ViewHolder{
        TextView OrderNumber , CustomarNameForShowAllOrders, FoodNameForShowAllOrders , TotalPriceForShowAllOrders, CountOrders;
        AppCompatButton AcceptedButton , CancelButton , FoodDetailsButton;
        LinearLayout additionalInfoLayoutad;
        RecyclerView foodListRecyclerView;
        private Food_Order_Application_Database db;
        public ShowAllOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            CustomarNameForShowAllOrders = itemView.findViewById(R.id.CustomarNameForShowAllOrders);
            FoodNameForShowAllOrders = itemView.findViewById(R.id.FoodNameForShowAllOrders);
            TotalPriceForShowAllOrders = itemView.findViewById(R.id.TotalPriceForShowAllOrders);
            CountOrders = itemView.findViewById(R.id.CountOrders);
            AcceptedButton = itemView.findViewById(R.id.AcceptedButton);
            CancelButton = itemView.findViewById(R.id.CancelButton);
            FoodDetailsButton = itemView.findViewById(R.id.FoodDetailsButton);
            additionalInfoLayoutad = itemView.findViewById(R.id.additionalInfoLayoutad);
            foodListRecyclerView = itemView.findViewById(R.id.foodListRecyclerView);
            db = new Food_Order_Application_Database(itemView.getContext());
        }
        public void bind(CustomarOrderHistory OrderDetails , int Position){
           OrderNumber.setText(String.valueOf(Position+1));
           CustomarNameForShowAllOrders.setText(OrderDetails.getCustomarName());
           FoodNameForShowAllOrders.setText(OrderDetails.getFoodName());
           TotalPriceForShowAllOrders.setText("$"+OrderDetails.getTotalPriceForThisOrder());
           CountOrders.setText(String.valueOf(OrderDetails.getCountOrders()));
            FoodDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( additionalInfoLayoutad.getVisibility() == View.GONE)
                    {
                        listener.OnShowDetailsClick(OrderDetails.getItemID());
                        additionalInfoLayoutad.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        additionalInfoLayoutad.setVisibility(View.GONE);
                    }
                }
            });
            AcceptedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int OrderID = OrderDetails.getOrderID();
                    if(db.ChangeOrderStatus(OrderID , "Arrived"))
                    {
                        Toast.makeText(itemView.getContext(), "Status will be changed to Arrived",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(itemView.getContext(), "Error in Updating Status",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int OrderID = OrderDetails.getOrderID();
                    if(db.ChangeOrderStatus(OrderID , "Pending"))
                    {
                        Toast.makeText(itemView.getContext(), "Status will be changed to Pending",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(itemView.getContext(), "Error in Updating Status",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}

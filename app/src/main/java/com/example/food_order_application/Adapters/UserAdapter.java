package com.example.food_order_application.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Interfaces.OnDeleteCustomarClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    ArrayList<Customar> CustomarList;
    OnDeleteCustomarClickListener OnDeleteCustomarClickListener;
    public UserAdapter(ArrayList<Customar> customarList,OnDeleteCustomarClickListener onDeleteCustomarClickListener){
         this.CustomarList =customarList;
         this.OnDeleteCustomarClickListener =onDeleteCustomarClickListener;
    }
    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view, OnDeleteCustomarClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
          Customar customar = CustomarList.get(position);
          holder.bind(customar,position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView ShowedCustomarNumber , ShowedCustomarEmail , ShowedCustomarPassword;
        private AppCompatButton DeleteCustomar;
        public UserViewHolder(@NonNull View itemView , OnDeleteCustomarClickListener listener) {
            super(itemView);
            ShowedCustomarNumber = itemView.findViewById(R.id.ShowedCustomarNumber);
            ShowedCustomarEmail = itemView.findViewById(R.id.ShowedCustomarEmail);
            ShowedCustomarPassword = itemView.findViewById(R.id.ShowedCustomarPassword);
            DeleteCustomar = itemView.findViewById(R.id.DeleteCustomar);

            DeleteCustomar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.OnDeleteCustomarClick(position);
                    }
                }
            });
        }

        public void bind(Customar customar, int position) {
            ShowedCustomarNumber.setText(String.valueOf(position+1));
            ShowedCustomarEmail.setText(customar.getEmail());
            ShowedCustomarPassword.setText(customar.getPassword());
        }
    }
}

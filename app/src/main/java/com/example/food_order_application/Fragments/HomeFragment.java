package com.example.food_order_application.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food_order_application.Adapters.CategoriesAdapter;
import com.example.food_order_application.Adapters.ItemListAdapter;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Domains.MenuItems;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.Interfaces.OnItemClickListener;
import com.example.food_order_application.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "CustomarID";
    private static final String ARG_PARAM2 = "CustomarName";

    // TODO: Rename and change types of parameters
    private int CustomarID;

    private int itemID;
    private String CustoamrName;

    private ImageView ImageProfile;
    private Food_Order_Application_Database db;
    private TextView CustomarNameAfterLogin;
    private RecyclerView CategoriesViewList,RecommenedViewList;
    private RecyclerView.Adapter Adapter1,Adapter2;

    private OnHomeFragmentListener Listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if ( context instanceof OnHomeFragmentListener){
            Listener = (OnHomeFragmentListener) context;
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
            CustoamrName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_home, container, false);
        CustomarNameAfterLogin = v.findViewById(R.id.CustomarNameAfterLogin);
        String showedText = "Hi, "+CustoamrName;
        CustomarNameAfterLogin.setText(showedText);
        ImageProfile = v.findViewById(R.id.ImageProfile);
        RecyclerViewCategories(v);
        RecyclerViewRecommended(v, CustomarID);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void RecyclerViewCategories(View v){
        CategoriesViewList = v.findViewById(R.id.CategoriesView);
        ArrayList<MenuItems> Categories =new ArrayList<>();
        Categories.add(new MenuItems("Pizza","cat_1"));
        Categories.add(new MenuItems("Burger","cat_2"));
        Categories.add(new MenuItems("Hotdog","cat_3"));
        Categories.add(new MenuItems("Drink","cat_4"));
        Categories.add(new MenuItems("Donut","cat_5"));
        Adapter1 = new CategoriesAdapter(Categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        CategoriesViewList.setHasFixedSize(true);
        CategoriesViewList.setLayoutManager(linearLayoutManager);
        CategoriesViewList.setAdapter(Adapter1);

    }
    private void RecyclerViewRecommended(View v, int CustomarID){
        db = new Food_Order_Application_Database(getActivity());
        RecommenedViewList = v.findViewById(R.id.CategoriesView);
        ArrayList<Item> FoodList = db.getAllItems();
        //FoodList.add(new Item("Pizza1","Pepperoni pizza","slices pepperoni , mozzarella cheese, fresh oregano, ground black pepper, pizza sauce ",13.0,5,20,1000));
        //FoodList.add(new Item("burger","Cheese Burger","beef, Gouda Cheese, Special Sauce, Lettuce, tomato ",15.20,4,18,1500));
        //FoodList.add(new Item("Pizza3","Vagetable pizza"," olive oil, Vegtable oil, pitted kalamato, cherry tomatoes, fresh oregano, basil",11.0,3,16,800));
        Adapter2 = new ItemListAdapter(FoodList, new OnItemClickListener() {
            @Override
            public void OnItemClick(int ItemID) {
                itemID = ItemID;
                Listener.OnHomeFragmentIntegration(itemID);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        RecommenedViewList.setHasFixedSize(true);
        RecommenedViewList.setLayoutManager(linearLayoutManager);
        RecommenedViewList.setAdapter(Adapter2);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }

    public interface OnHomeFragmentListener{
        void OnHomeFragmentIntegration(int ItemID);
    }
}
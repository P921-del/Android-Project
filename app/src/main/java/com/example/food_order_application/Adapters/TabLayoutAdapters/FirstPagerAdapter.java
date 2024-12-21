package com.example.food_order_application.Adapters.TabLayoutAdapters;

import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.food_order_application.Domains.TabLayoutDomains.TabInsideMainActivity;

import java.util.ArrayList;

public class FirstPagerAdapter extends FragmentStateAdapter {
    ArrayList<TabInsideMainActivity> Tabs;

    public FirstPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<TabInsideMainActivity> tabs) {
        super(fragmentManager, lifecycle);
        Tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return Tabs.get(position).getFragment();
    }



    @Override
    public int getItemCount() {
        return 0;
    }
}

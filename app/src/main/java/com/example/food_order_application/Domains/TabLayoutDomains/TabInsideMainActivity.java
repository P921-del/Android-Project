package com.example.food_order_application.Domains.TabLayoutDomains;

import androidx.fragment.app.Fragment;

public class TabInsideMainActivity {
    int tabIndex;
    String tabName;
    int tabIcon;
    Fragment fragment;

    public TabInsideMainActivity(String tabName, int tabIcon, Fragment fragment) {
        this.tabName = tabName;
        this.tabIcon = tabIcon;
        this.fragment = fragment;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(int tabIcon) {
        this.tabIcon = tabIcon;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
}

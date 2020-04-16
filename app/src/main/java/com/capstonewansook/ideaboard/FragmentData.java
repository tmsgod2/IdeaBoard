package com.capstonewansook.ideaboard;

import androidx.fragment.app.Fragment;

public class FragmentData {
    Fragment fragment;
    int menuId;

    public FragmentData(Fragment fragment, int menuId) {
        this.fragment = fragment;
        this.menuId = menuId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}

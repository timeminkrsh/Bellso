package com.taprocycle.service.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class FragmentPagerAdapter {
    public FragmentPagerAdapter(FragmentManager fragmentActivity) {
    }

    @NonNull
    public abstract Fragment getItem(int position);

    public abstract int getCount();

    @Nullable
    public abstract CharSequence getPageTitle(int position);
}

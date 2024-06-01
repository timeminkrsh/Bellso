package com.taprocycle.service;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.taprocycle.service.Fragment.ProductsDynamicFragment;

import java.util.ArrayList;
import java.util.List;

public class TuitionViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentscidList = new ArrayList<>();
    private ArrayList<String> mFragmentcidList = new ArrayList<>();

    String scid = "", cid = "";

    public TuitionViewPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> mFragmentList, List<String> mFragmentTitleList, ArrayList<String> mFragmentscidList, ArrayList<String> mFragmentcidList) {
        super(supportFragmentManager);
        this.mFragmentList = mFragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
        this.mFragmentscidList = mFragmentscidList;
        this.mFragmentcidList = mFragmentcidList;

    }


    @Override
    public Fragment getItem(int position) {
        mFragmentList.get(position);
        mFragmentscidList.get(position);
        scid = mFragmentscidList.get(position);
        cid = mFragmentcidList.get(position);
        return ProductsDynamicFragment.getInstance(position, scid, cid);
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

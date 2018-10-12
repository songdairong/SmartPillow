package com.example.smartpillownew.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.smartpillownew.fragment.BaseFragment;

import java.util.List;

/**
 * Created by a450J on 2018/8/7.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter{

    List<BaseFragment> fragmentList;
    List<String> titleList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

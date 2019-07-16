package com.example.project_text.fragment.tencent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project_text.data.entity.TencentTab;

import java.util.List;

public class TenVpAdapter extends FragmentStatePagerAdapter {

    private List<TencentTab> mTabs;
    private List<Fragment> mFragments;

    public TenVpAdapter(FragmentManager fm, List<TencentTab> tabs, List<Fragment> fragments) {
        super(fm);
        mTabs = tabs;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getName();
    }
}

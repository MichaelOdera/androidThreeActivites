package com.michael.myfitnessapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.ui.GymnasiumDetailFragment;

import java.util.List;

public class GymnasiumPagerAdapter extends FragmentPagerAdapter {
    private List<Business> mGymnasiums;
    public GymnasiumPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Business> gymnasiums) {
        super(fm, behavior);
        mGymnasiums = gymnasiums;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return GymnasiumDetailFragment.newInstance(mGymnasiums.get(position));
    }

    @Override
    public int getCount() {
        return mGymnasiums.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mGymnasiums.get(position).getName();
    }
}

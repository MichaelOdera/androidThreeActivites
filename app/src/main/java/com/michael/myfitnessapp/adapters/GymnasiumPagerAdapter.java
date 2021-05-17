package com.michael.myfitnessapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.ui.GymnasiumDetailFragment;

import java.util.List;

public class GymnasiumPagerAdapter extends FragmentPagerAdapter {
    private List<Business> mGymnasiums;
    String mSaved;
    public GymnasiumPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Business> gymnasiums, String saved) {
        super(fm, behavior);
        mGymnasiums = gymnasiums;
        mSaved = saved;
    }

    @NonNull
    @Override
    public GymnasiumDetailFragment getItem(int position) {
        return GymnasiumDetailFragment.newInstance(mGymnasiums.get(position), mSaved);
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

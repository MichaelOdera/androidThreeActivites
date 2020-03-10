package com.example.myfitnessapp.adapters;

import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.ui.GymnasiumDetailFragment;

import java.util.List;

public class GymnasiumPageAdapter extends FragmentPagerAdapter {
    private List<Business> mGymnasiums;
    public GymnasiumPageAdapter(@NonNull FragmentManager fm, int behavior, List<Business> gymnasiums) {
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
    public CharSequence 
}

package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.adapters.GymnasiumPagerAdapter;
import com.example.myfitnessapp.models.Business;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GymnasiumDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private GymnasiumPagerAdapter gymnasiumPagerAdapter;
    List<Business> mGymnasiums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnasium_detail);
        ButterKnife.bind(this);

        mGymnasiums = Parcels.unwrap(getIntent().getParcelableExtra("gyms"));
        int startingPosition = getIntent().getIntExtra("position", 0);


        gymnasiumPagerAdapter = new GymnasiumPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mGymnasiums);
        mViewPager.setAdapter(gymnasiumPagerAdapter);
        mViewPager.setCurrentItem(startingPosition);
    }
}

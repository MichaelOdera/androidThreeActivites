package com.michael.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.adapters.GymnasiumPagerAdapter;
import com.michael.myfitnessapp.models.Business;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("NonConstantResourceId")
public class GymnasiumDetailActivity extends AppCompatActivity{

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private GymnasiumPagerAdapter gymnasiumPagerAdapter;
    List<Business> mGymnasiums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnasium_detail);
        ButterKnife.bind(this);

        mGymnasiums = Parcels.unwrap(getIntent().getParcelableExtra("gyms"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        String saved = getIntent().getStringExtra("saved");


        gymnasiumPagerAdapter = new GymnasiumPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mGymnasiums, saved);
        mViewPager.setAdapter(gymnasiumPagerAdapter);
        mViewPager.setCurrentItem(startingPosition);
    }

}

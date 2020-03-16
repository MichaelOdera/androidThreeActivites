package com.example.myfitnessapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myfitnessapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.getStartedButton)
    Button mGetStartedButton;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGetStartedButton.setOnClickListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_gyms:
                            startActivity(new Intent(MainActivity.this, SearchGymsActivity.class));

                            return true;
                        case R.id.navigation_activities:
                            FragmentManager fm = getSupportFragmentManager();
                            RegistrationDialogFragment registerDialogFragment = new RegistrationDialogFragment ();
                            registerDialogFragment.show(fm, "registration");

                            return true;
                    }
                    return false;
                }
            };


    @Override
    public void onClick(View v){
        if(v == mGetStartedButton){
            FragmentManager fm = getSupportFragmentManager();
            RegistrationDialogFragment registerDialogFragment = new RegistrationDialogFragment ();
            registerDialogFragment.show(fm, "registration");
        }
    }

}

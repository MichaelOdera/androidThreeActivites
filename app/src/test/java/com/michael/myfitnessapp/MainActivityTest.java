package com.michael.myfitnessapp;

import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import com.michael.myfitnessapp.ui.MainActivity;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


@Config(sdk = {Build.VERSION_CODES.O_MR1})


@RunWith(RobolectricTestRunner.class)

public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setup(){
        FirebaseApp.initializeApp(activity.getBaseContext());
        activity = Robolectric.setupActivity(MainActivity.class);
    }


    @Test
    public void validateRecyclerViewWidthParameter(){
        RecyclerView mainActivityRecyclerView = activity.findViewById(R.id.mainRecyclerView);
        assertEquals("match_parent", mainActivityRecyclerView.getLayoutParams().width);
    }

}
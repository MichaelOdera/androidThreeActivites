package com.example.myfitnessapp;

import android.os.Build;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.ui.MainActivity;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
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
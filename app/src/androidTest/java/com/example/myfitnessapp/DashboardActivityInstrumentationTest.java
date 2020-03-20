package com.example.myfitnessapp;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.myfitnessapp.ui.DashboardActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class DashboardActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<DashboardActivity> dashBoardActivityTestRule =
            new ActivityTestRule<>(DashboardActivity.class);



}
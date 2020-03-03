package com.example.myfitnessapp;

import android.view.View;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class DashboardActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<DashboardActivity> dashBoardActivityTestRule =
            new ActivityTestRule<>(DashboardActivity.class);

    @Test
    public void checksTheFirstItemOfTheAdapterListAndReturnsCorrectValue(){
        View activityDecorView = dashBoardActivityTestRule.getActivity().getWindow().getDecorView();
        String exerciseName = "Running";
        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(exerciseName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(exerciseName)));
    }

}
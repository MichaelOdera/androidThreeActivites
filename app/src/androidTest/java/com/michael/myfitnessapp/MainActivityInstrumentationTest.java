package com.michael.myfitnessapp;

import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.michael.myfitnessapp.ui.MainActivity;
import com.michael.myfitnessapp.ui.RegistrationDialogFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class MainActivityInstrumentationTest {

    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        activityTestRule.launchActivity(MY_ACTIVITY_INTENT);
        String userName = "Clement";
    }

    @Before
    public void init(){
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }


    @Test
    public void getInstanceOfFragmentReturnsTrue(){
        RegistrationDialogFragment testFragment = new RegistrationDialogFragment();
        //testFragment.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view_tag, testFragment).commit();
        assertTrue(testFragment instanceof RegistrationDialogFragment);
    }

//    @Test
//    public void checkThatTextViewIsVisible(){
//        onView(withId(R.id.appNameTextView)).check(matches(isDisplayed()));
//    }

    @Test
    public void checkIfPositiveButtonIsClickedCorrectlyOnDialogFragment(){
        onView(withId(R.id.navigation_activities)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void checkIfEditTextReceivesDataCorrectly(){
        //String userName = "Clement";
        onView(withId(R.id.navigation_activities)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.nameOfUserEditText)).perform(typeText("Clement")).perform(closeSoftKeyboard());
    }

    @Test
    public void checkIfNameEnteredIsTransmittedToTheNextActivityCorrectly(){
        String userName = "Clement";
        onView(withId(R.id.navigation_activities)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.nameOfUserEditText)).perform(typeText(userName)).perform(closeSoftKeyboard());

        onView(withId(R.id.submitNameButton)).perform(click());
        onView(withId(R.id.userNameTextView)).check(matches
                (withText("Welcome "+ userName+". \nClick on activity to add it to your favorites List")));
    }

    @Test
    public void checkIfExerciseIsSelectedIsAddedToFavoriteList(){
        String userName = "Clement";
        onView(withId(R.id.navigation_activities)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.nameOfUserEditText)).perform(typeText(userName)).perform(closeSoftKeyboard());

        onView(withId(R.id.submitNameButton)).perform(click());
        onView(withId(R.id.listView)).perform(click());
    }




}
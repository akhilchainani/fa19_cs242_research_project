package com.example.fa19_cs242_research_project;

import org.junit.Before;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    private String stringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkSignInButtons() {
        // Type text and then press the button.
        onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(withId(R.id.sign_out_button_question)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_out_button_question)).perform(click());

        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.sign_out_button_question)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_out_button_question)).perform(click());
    }
}
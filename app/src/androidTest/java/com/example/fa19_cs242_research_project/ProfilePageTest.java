package com.example.fa19_cs242_research_project;

import androidx.test.rule.ActivityTestRule;

import com.example.fa19_cs242_research_project.ProfilePage.ProfilePage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ProfilePageTest {

    private String stringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkProfilePage() {
        // Type text and then press the button.
        onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(withId(R.id.profile_button)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_button)).perform(click());

        onView(withId(R.id.profile_pic)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_name)).check(matches(isDisplayed()));
        onView(withId(R.id.high_score_val)).check(matches(isDisplayed()));

        onView(withId(R.id.sign_out_button_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_out_button_profile)).perform(click());
    }
}
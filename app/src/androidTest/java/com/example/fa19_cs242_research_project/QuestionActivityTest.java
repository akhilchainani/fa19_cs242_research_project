package com.example.fa19_cs242_research_project;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class QuestionActivityTest {

    private String stringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkProfilePage() {
        // Type text and then press the button.
        onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(withId(R.id.option_one_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.option_one_settings_button)).perform(click());

        onView(withId(R.id.option_one_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.option_one_settings_button)).perform(click());

        onView(withId(R.id.profile_button)).check(matches(isDisplayed()));
        onView(withId(R.id.myQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_out_button_question)).check(matches(isDisplayed()));
        onView(withId(R.id.option_one_button)).check(matches(isDisplayed()));
        onView(withId(R.id.option_two_button)).check(matches(isDisplayed()));
        onView(withId(R.id.option_three_button)).check(matches(isDisplayed()));
        onView(withId(R.id.option_four_button)).check(matches(isDisplayed()));
        onView(withId(R.id.high_score)).check(matches(isDisplayed()));

        onView(withId(R.id.sign_out_button_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_out_button_profile)).perform(click());
    }
}
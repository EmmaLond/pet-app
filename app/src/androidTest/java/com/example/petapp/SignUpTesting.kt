package com.example.petapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.myapp.com.example.petapp.EspressoIdlingResource
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import kotlin.jvm.java

@RunWith(AndroidJUnit4::class)
class SignUpTesting {

    // Says what activity I am testing
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Signup::class.java)

    // For threading
    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.signUpValidation)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.signUpValidation)
    }

    @Test
    fun emptyInputs() {
        // Setup
        onView(withId(R.id.button_signup)).perform(click())

        // Check
        onView(withId(R.id.emailWrap))
            .check(matches(withTextInputLayoutError("Email is required.")))
        onView(withId(R.id.show_password))
            .check(matches(withTextInputLayoutError("Password is required.")))
        onView(withId(R.id.show_password_again))
            .check(matches(withTextInputLayoutError("Password is required.")))
    }
}
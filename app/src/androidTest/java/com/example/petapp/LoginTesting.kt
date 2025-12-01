package com.example.petapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.jvm.java

class LoginTesting {

    // Says what activity I am testing
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun emptyInputs() {
        // Setup
        onView(withId(R.id.btnLogin)).perform(click())

        // Check
        onView(withId(R.id.usernameEditText))
            .check(matches(hasErrorText("Username is required.")))
        onView(withId(R.id.passwordEditText))
            .check(matches(hasErrorText("Password is required.")))
    }
}
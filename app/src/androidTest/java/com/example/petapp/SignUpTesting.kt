package com.example.petapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.example.myapp.com.example.petapp.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
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

    // For threading and intent setup
    @Before
    fun setup() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.signUpValidation)
    }

    @After
    fun tearDown() {
        Intents.release()
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

    @Test
    fun verifyOTPIntent() {
        // Setup
        onView(withId(R.id.emailEditText))
            .perform(clearText(), typeText("testingemail@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(clearText(), typeText("testPassword"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditTextAgain))
            .perform(clearText(), typeText("testPassword"), closeSoftKeyboard())
        onView(withId(R.id.button_signup)).perform(click())

        // Check
        intended(allOf(
            hasComponent(OTP::class.java.name),
            hasExtra("email", "testingemail@gmail.com"),
            hasExtra("password", "testPassword")
        ))
    }
}
package com.example.petapp

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withTextInputLayoutError(expectedError: String): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("TextInputLayout with error: \"$expectedError\"")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is TextInputLayout) return false
            val error = view.error ?: return false
            return error.toString() == expectedError
        }
    }
}

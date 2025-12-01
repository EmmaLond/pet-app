package com.example.myapp.com.example.petapp

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    val signUpValidation = CountingIdlingResource("SignUpValidation")
}
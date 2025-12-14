package com.example.petapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp.com.example.petapp.EspressoIdlingResource
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import kotlin.jvm.java

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()

        val email = findViewById<EditText>(R.id.emailEditText)
        val password_one = findViewById<EditText>(R.id.passwordEditText)
        val password_two = findViewById<EditText>(R.id.passwordEditTextAgain)
        val signupButton = findViewById<Button>(R.id.button_signup)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        signupButton.setOnClickListener {
            val passwordLayout1 = findViewById<TextInputLayout>(R.id.show_password)
            val passwordLayout2 = findViewById<TextInputLayout>(R.id.show_password_again)
            val emailLayout = findViewById<TextInputLayout>(R.id.emailWrap)

            // Check for empty values
            var isEmpty = false
            if (email.text.isEmpty()) {
                isEmpty = true
                emailLayout.error = "Email is required."
            }
            if (password_one.text.isEmpty()) {
                isEmpty = true
                passwordLayout1.error = "Password is required."
            }
            if (password_two.text.isEmpty()) {
                isEmpty = true
                passwordLayout2.error = "Password is required."
            }
            if (isEmpty) {
                return@setOnClickListener
            }

            val passwordOne = password_one.text.toString().trim()
            val passwordTwo = password_two.text.toString().trim()
            val userEmail = email.text.toString().trim()

            // Verify content of text
            if (passwordOne.length < 7) {
                passwordLayout1.error = "Password is too short. Please have at least 7 characters."
                return@setOnClickListener
            }
            if (passwordOne != passwordTwo) {
                passwordLayout1.error = "Passwords do not match."
                passwordLayout2.error = "Passwords do not match."
                return@setOnClickListener
            }
            if (!userEmail.contains('@') || !userEmail.contains('.')) {
                emailLayout.error = "Please enter a valid email."
                return@setOnClickListener
            }

            EspressoIdlingResource.signUpValidation.increment()
            lifecycleScope.launch {
                // Check account exists, returns null if there is no account
                val accountExist =
                    accountTable.getPassword(email = email.text.toString().trim())

                if (accountExist == null) {
                    val intent = Intent(this@Signup, OTP::class.java)
                    intent.putExtra("email", email.text.toString().trim())
                    intent.putExtra("password", passwordOne)
                    startActivity(intent)
                } else {
                    emailLayout.error = "An account with that email already exists."
                }
                EspressoIdlingResource.signUpValidation.decrement()
            }
        }

    }
}
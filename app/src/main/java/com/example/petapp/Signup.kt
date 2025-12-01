package com.example.petapp

import android.content.Intent
import android.os.Bundle
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
            // Set errors
            var isEmpty = false
            val passwordLayout1 = findViewById<TextInputLayout>(R.id.show_password)
            val passwordLayout2 = findViewById<TextInputLayout>(R.id.show_password_again)
            val emailLayout = findViewById<TextInputLayout>(R.id.emailWrap)
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

            EspressoIdlingResource.signUpValidation.increment()
            lifecycleScope.launch {
                // Check account exists, returns null if there is no account
                val accountExist =
                    accountTable.getPassword(email = email.text.toString().trim())

                if (passwordOne == passwordTwo && accountExist == null) {
                    val intent = Intent(this@Signup, OTP::class.java)
                    intent.putExtra("email", email.text.toString().trim())
                    intent.putExtra("password", passwordOne)
                    startActivity(intent)
                // Sets Errors
                } else if (passwordOne != passwordTwo) {
                    passwordLayout1.error = "Passwords do not match"
                    passwordLayout2.error = "Passwords do not match"
                } else {
                    emailLayout.error = "An account with that email already exists"
                }
                EspressoIdlingResource.signUpValidation.decrement()
            }
        }

    }
}
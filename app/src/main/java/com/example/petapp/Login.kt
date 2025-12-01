package com.example.petapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import kotlin.jvm.java

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()

        val username = findViewById<TextInputEditText>(R.id.usernameEditText)
        val password = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        loginButton.setOnClickListener {
            // Set blank errors
            val email = username.text.toString().trim()
            if (email.isNullOrBlank()) {
                username.setError("Username is required.")
            }
            val thePassword = password.text.toString().trim()
            if (thePassword.isNullOrBlank()) {
                password.setError("Password is required.")
            }

            if (thePassword.isNullOrBlank() || email.isNullOrBlank()) {
                return@setOnClickListener
            }

            // Check passwords
            lifecycleScope.launch {
                val hashedPassword = accountTable.getPassword(email = email)
                if (hashedPassword != null) {
                    val checkPassword = BCrypt.checkpw(thePassword, hashedPassword)

                    // Login
                    if (checkPassword) {
                        val intent = Intent(this@Login, HomeScreen::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("userId", accountTable.getUserId(email = email))
                        startActivity(intent)

                    // Set incorrect login errors
                    } else {
                        username.setError("Incorrect username or password. Please try again.")
                        password.setError("Incorrect username or password. Please try again.")
                    }
                } else {
                    username.setError("Incorrect username or password. Please try again.")
                    password.setError("Incorrect username or password. Please try again.")
                }

            }
        }
    }
}
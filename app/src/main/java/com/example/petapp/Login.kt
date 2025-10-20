package com.example.petapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()

        val username = findViewById<TextInputEditText>(R.id.usernameEditText)
        val password = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.button_login)
        val warning = findViewById<TextView>(R.id.warning)

        loginButton.setOnClickListener {
            // Reset warnings
            warning.setText("")

            // Set errors
            val email = username.text.toString().trim()
            if (email.isNullOrBlank()) {
                username.setError("Username is required.")
            }
            val thePassword = password.text.toString().trim()
            if (thePassword.isNullOrBlank()) {
                password.setError("Password is required.")
            }

            // Check passwords
            if (!(thePassword.isNullOrBlank() && email.isNullOrBlank())) {
                lifecycleScope.launch {
                    val hashedPassword = accountTable.getPassword(email = email)
                    if (hashedPassword != null) {
                        val checkPassword = BCrypt.checkpw(thePassword, hashedPassword)

                        // Login
                        if (checkPassword) {
                            val intent = Intent(this@Login, MainActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)

                        // Set warning
                        } else {
                            warning.setText("Incorrect username or password. Please try again.")
                        }
                    } else {
                        warning.setText("Incorrect username or password. Please try again.")
                    }
                }
            }
        }
    }
}
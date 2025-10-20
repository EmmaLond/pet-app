package com.example.petapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

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
        val warning = findViewById<TextView>(R.id.mistake)
        
        signupButton.setOnClickListener {
            if (email.text.isEmpty()) {
                email.setError("Email is required.")
            }
            if (password_one.text.isEmpty()) {
                password_one.setError("Password is required.")
            }
            if (password_two.text.isEmpty()) {
                password_two.setError("Password is required.")
            }

            if (!(email.text.isEmpty() && password_one.text.isEmpty() && password_two.text.isEmpty())) {
                if (!(warning.text.isEmpty())) {
                    warning.setText("")
                }
                val passwordOne = password_one.text.toString().trim()
                val passwordTwo = password_two.text.toString().trim()
                lifecycleScope.launch {
                    val accountExist =
                        accountTable.getPassword(email = email.text.toString().trim())

                    if (passwordOne == passwordTwo && accountExist == null) {
                        lifecycleScope.launch {
                            accountTable.insert(
                                Account(
                                    email = email.text.toString().trim(),
                                    password = BCrypt.hashpw(passwordOne, BCrypt.gensalt())
                                )
                            )
                        }
                    } else if (passwordOne != passwordTwo) {
                        warning.setText("Passwords do not match.")
                    } else {
                        warning.setText("An account with that email already exists.")
                        // This code is used to test the database, please leave it just in case
//                        lifecycleScope.launch {
//                            accountTable.getAllUsers().collect { value ->
//                                value.forEach { (id, email, password) ->
//                                    Log.println(
//                                        Log.WARN, "DEBUG",
//                                        "$id $email $password"
//                                    )
//                                }
//                            }
//                        }

                    }
                }
            }
        }

    }
}
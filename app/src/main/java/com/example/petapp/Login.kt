package com.example.petapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val username = findViewById<TextInputEditText>(R.id.usernameEditText)
        val password = findViewById<TextInputEditText>(R.id.passwordEditText)

        btnLogin.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            // Simple check for demonstration purposes
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // ✅ Move to HomeScreen
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
                finish() // optional, closes Login screen so user can't go back
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
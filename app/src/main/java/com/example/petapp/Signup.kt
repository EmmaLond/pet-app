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
        val stuff = AppDatabase.getDatabase(applicationContext).userDao()

        val peter = findViewById<TextView>(R.id.Peter)
        val email = findViewById<EditText>(R.id.emailEditText)
        val password_one = findViewById<EditText>(R.id.passwordEditText)
        val password_two = findViewById<EditText>(R.id.passwordEditTextAgain)
        val signupButton = findViewById<Button>(R.id.button_signup)
        signupButton.setOnClickListener {
            val passwordOne = password_one.text.toString().trim()
            val passwordTwo = password_two.text.toString().trim()
            if (passwordOne == passwordTwo) {
                lifecycleScope.launch { stuff.insert(Account(email = email.text.toString().trim(),
                    password = BCrypt.hashpw(passwordOne, BCrypt.gensalt()))) }

            }
            else {
                lifecycleScope.launch { stuff.getAllUsers().collect { value -> value.forEach { (id, email, password) -> Log.println(Log.WARN, "DEBUG", "$id $email $password") }}}

            }
        }

    }
}
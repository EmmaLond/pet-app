package com.example.petapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
        
        signupButton.setOnClickListener {
            val passwordOne = password_one.text.toString().trim()
            val passwordTwo = password_two.text.toString().trim()
            val theEmail = email.text.toString().trim()
            val accountExist = accountTable.getPassword(theEmail)

            if (passwordOne == passwordTwo && accountExist == null) {
                lifecycleScope.launch { accountTable.insert(Account(email = email.text.toString().trim(),
                    password = BCrypt.hashpw(passwordOne, BCrypt.gensalt()))) }
            }
//            else if (accountExist != null) {
//
//            }
            else {
                lifecycleScope.launch { accountTable.getAllUsers().collect { value -> value.forEach { (id, email, password) -> Log.println(Log.WARN, "DEBUG", "$id $email $password") }}}

            }
        }

    }
}
package com.example.petapp

import android.R.attr.text
import android.content.Intent
import android.os.Bundle
import android.util.Log.v
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class OTP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp)
        val otpButton = findViewById<Button>(R.id.button_otp)

        otpButton.setOnClickListener {
            val otpet = findViewById<TextInputEditText>(R.id.otpEditText)
            val otp = otpet.text.toString()

            if (otp.trimmedLength() != 6) {
                otpet.setError("OTP is incorrect.")
                return@setOnClickListener
            }

            val accountTable = AppDatabase.getDatabase(applicationContext).userDao()
            lifecycleScope.launch {
                val email = intent.getStringExtra("email") ?: ""
                val password = intent.getStringExtra("password") ?: ""
                accountTable.insert(
                    Account(
                        email = email,
                        password = BCrypt.hashpw(password, BCrypt.gensalt())
                    )
                )
                val intent = Intent(this@OTP, HomeScreen::class.java)
                intent.putExtra("email", email)
                intent.putExtra("userId", accountTable.getUserId(email = email))
                startActivity(intent)
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
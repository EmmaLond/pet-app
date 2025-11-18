package com.example.petapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.petapp.PetsAdapter
import com.example.petapp.AppDatabase
import com.example.petapp.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeScreen : AppCompatActivity() {
    private var userAccount: Account? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val usernameText = findViewById<TextView>(R.id.userText)
        val addButton = findViewById<FloatingActionButton>(R.id.addPetButton)
        val accountDao = AppDatabase.getDatabase(applicationContext).userDao()
        val petsList = findViewById<RecyclerView>(R.id.recycleViewPets)
        petsList.layoutManager = LinearLayoutManager(this)




        lifecycleScope.launch {
            val userEmail = intent.getStringExtra("email")
            val userAccount = accountDao.getAllUsers().first().firstOrNull { it.email == userEmail }

            if (userAccount != null) {
                usernameText.text = "Hello, ${userAccount.email}!"
                val userWithPets = accountDao.getUserWithPets(userAccount.userId)
                val pets = userWithPets.firstOrNull()?.pets ?: emptyList()
                petsList.adapter = PetsAdapter(pets)
            }
        }
        addButton.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            intent.putExtra("userId", userAccount?.userId ?: -1)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}
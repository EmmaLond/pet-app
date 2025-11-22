package com.example.petapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petapp.AddPetActivity
import com.example.petapp.AppDatabase
import com.example.petapp.PetsAdapter
import com.example.petapp.UserDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlin.jvm.java

class HomeScreen : AppCompatActivity() {

    private lateinit var petsList: RecyclerView
    private lateinit var petsAdapter: PetsAdapter
    private lateinit var accountDao: UserDao
    private var userId: Int = -1
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        userId = intent.getIntExtra("userId", -1)
        userEmail = intent.getStringExtra("email")

        val usernameText = findViewById<TextView>(R.id.userText)
        usernameText.text = "Hello, $userEmail!"

        accountDao = AppDatabase.getDatabase(applicationContext).userDao()

        petsList = findViewById(R.id.recycleViewPets)
        petsList.layoutManager = LinearLayoutManager(this)

        petsAdapter = PetsAdapter(emptyList()) { clickedPet ->
            val intent = Intent(this, PetProfile::class.java)
            intent.putExtra("petId", clickedPet.petId)
            startActivity(intent)
        }

        petsList.adapter = petsAdapter

        val addButton = findViewById<FloatingActionButton>(R.id.addPetButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        loadPets()
    }

    override fun onResume() {
        super.onResume()
        loadPets()
    }

    private fun loadPets() {
        lifecycleScope.launch {
            val userWithPets = accountDao.getUserWithPets(userId)
            val pets = userWithPets.pets
            petsAdapter.updatePets(pets)
        }
    }
}

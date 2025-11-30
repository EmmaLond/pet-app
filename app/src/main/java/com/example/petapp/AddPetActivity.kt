package com.example.petapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddPetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        val userId = intent.getIntExtra("userId", -1)
        val petNameEdit = findViewById<EditText>(R.id.petNameEditText)
        val petSpeciesEdit = findViewById<EditText>(R.id.petSpeciesEditText)
        val petBreedEdit = findViewById<EditText>(R.id.petBreedEditText)
        val saveButton = findViewById<Button>(R.id.savePetButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        saveButton.setOnClickListener {
            val petName = petNameEdit.text.toString().trim()
            val petSpecies = petSpeciesEdit.text.toString().trim()
            if (petName.isEmpty() || petSpecies.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(applicationContext).userDao()

                val newPet = PetInfo(
                    name = petNameEdit.text.toString().trim(),
                    species = petSpeciesEdit.text.toString().trim(),
                    breed = petBreedEdit.text.toString().trim(),
                    userId = userId
                )

                dao.insertPet(newPet)
                runOnUiThread {
                    Toast.makeText(this@AddPetActivity, "Pet added successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        }
    }
}

package com.example.petapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
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
        val petBreedEdit = findViewById<EditText>(R.id.petBreedEditText)
        val saveButton = findViewById<Button>(R.id.savePetButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val speciesOptions: Spinner = findViewById(R.id.petSpecies)
        val species = listOf("Dog", "Cat", "Lizard", "Bird", "Frog", "Spider", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, species)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        speciesOptions.adapter = adapter
        var selectedSpecies = ""

        speciesOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSpecies = parent.getItemAtPosition(position).toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        saveButton.setOnClickListener {
            val petName = petNameEdit.text.toString().trim()
            val petSpecies = selectedSpecies
            if (petName.isEmpty() || petSpecies.isEmpty()) {
                petNameEdit.setError("Please put your pet's name")
                return@setOnClickListener
            }


            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(applicationContext).userDao()

                val newPet = PetInfo(
                    name = petNameEdit.text.toString().trim(),
                    species = selectedSpecies,
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

package com.example.petapp

import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlin.jvm.java

class PetProfile : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_profile)
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()
        val petId = intent.getIntExtra("petId", -1)

        lifecycleScope.launch {
            val speciesBreedet = findViewById<TextView>(R.id.stats)
            val petName = findViewById<TextView>(R.id.petName)
            accountTable.getPet(petId = petId).collect { petInfo ->
                if (petInfo != null) {
                    speciesBreedet.text = "Species: ${petInfo.species}\nBreed: ${petInfo.breed}"
                    petName.text = petInfo.name
                } else {
                    finish()
                }
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.petLog)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = LogAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            accountTable.getLog(petId).collect { logs ->
                adapter.submitList(logs)
            }
        }

        val addButton = findViewById<FloatingActionButton>(R.id.addLog)
        addButton.setOnClickListener {
            val menu = PopupMenu(this, it)
            val inflater: MenuInflater = menu.menuInflater
            inflater.inflate(R.menu.pet_menu, menu.menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.addToLog -> {
                        val intent = Intent(this, addLog::class.java)
                        intent.putExtra("petId", petId)
                        startActivity(intent)
                        true
                    }
                    R.id.removePet -> {
                        showDeleteConfirmation(petId)
                        true
                    }
                    else -> false
                }
            }
            menu.show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun showDeleteConfirmation(petId: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        userId = intent.getIntExtra("userId", -1)
        userEmail = intent.getStringExtra("email")

        builder.setTitle("Delete Pet")
        builder.setMessage("Are you sure you want to remove this pet? This action cannot be undone.")
        builder.setPositiveButton("Yes") { dialog, _ ->
            deletePet(petId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(true)
        builder.show()
    }

    private fun deletePet(petId: Int) {
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()
        lifecycleScope.launch {
            accountTable.removePet(petId)
        }
        finish()
    }
}
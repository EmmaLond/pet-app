package com.example.petapp

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

class PetProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_profile)
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()
        val petId = intent.getIntExtra("petId", -1)

        lifecycleScope.launch {
            val speciesBreedet = findViewById<TextView>(R.id.stats)
            val petName = findViewById<TextView>(R.id.petName)
            accountTable.getPet(petId = petId).collect { info ->
                val breed = info.breed
                val species = info.species
                val name = info.name
                speciesBreedet.setText("Species: " + species + "\nBreed: "+ breed)
                petName.setText(name)
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
            menu.show()
//            val intent = Intent(this, addLog::class.java)
//            intent.putExtra("petId", petId)
//            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
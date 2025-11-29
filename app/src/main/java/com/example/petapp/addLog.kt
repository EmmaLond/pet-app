package com.example.petapp

import android.os.Bundle
import android.util.Log.v
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Date

class addLog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_log)
        val accountTable = AppDatabase.getDatabase(applicationContext).userDao()
        val otherText = findViewById<EditText>(R.id.logOther)
        val petId = intent.getIntExtra("petId", -1)

        val activityOptions: Spinner = findViewById(R.id.activityOptions)
        val activities = listOf("Went on Walk", "Fed Pet", "Gave Bath", "Went Potty", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityOptions.adapter = adapter
        var selectedActivity = ""

        activityOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedActivity = parent.getItemAtPosition(position).toString()

                if (selectedActivity == "Other") {
                    otherText.visibility = View.VISIBLE
                } else {
                    otherText.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val saveButton = findViewById<Button>(R.id.saveLog)
        saveButton.setOnClickListener {
            if (selectedActivity == "Other") {
                val custom = otherText.text.toString()

                if (custom.isBlank()) {
                    otherText.setError("Please input an activity")
                    return@setOnClickListener
                }
                val finalActivity = custom
                lifecycleScope.launch {
                    val newLog = Log(
                        petId = petId,
                        timeOccurred = Date(),
                        activity = finalActivity
                    )
                    accountTable.insertLog(newLog)
                }
                finish()
            } else {
                val finalActivity = selectedActivity
                lifecycleScope.launch {
                    val newLog = Log(
                        petId = petId,
                        timeOccurred = Date(),
                        activity = finalActivity
                    )
                    accountTable.insertLog(newLog)
                }
                finish()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addLogLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
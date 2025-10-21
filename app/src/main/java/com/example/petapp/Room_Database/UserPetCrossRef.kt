package com.example.petapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true) val petId: Int = 0,
    val name: String,
    val species: String,
    val age: Int
)

package com.example.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetInfo (
    @PrimaryKey(autoGenerate = true) val petId: Int=0,

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "breed") val breed: String,
    @ColumnInfo(name = "userId") val userId: Int
)

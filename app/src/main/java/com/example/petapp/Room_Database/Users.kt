package com.example.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Account (
    @PrimaryKey(autoGenerate = true) val userId: Int=0,

    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)

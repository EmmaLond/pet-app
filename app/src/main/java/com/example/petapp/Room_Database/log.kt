package com.example.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "log")
data class Log (
    @PrimaryKey(autoGenerate = false) val petId: Int=0,
    @PrimaryKey(autoGenerate = true) val timeOccured: Date,

    @ColumnInfo(name = "activity") val activity: String
)

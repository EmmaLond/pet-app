package com.example.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "log",
    primaryKeys = ["petId", "timeOccurred"],
    foreignKeys = [
        ForeignKey(
            entity = PetInfo::class,
            parentColumns = ["petId"],
            childColumns = ["petId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Log(
    val petId: Int,
    val timeOccurred: Date,
    @ColumnInfo(name = "activity") val activity: String
)

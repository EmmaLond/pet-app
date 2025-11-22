package com.example.petapp

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithPets(
    @Embedded val user: Account,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val pets: List<PetInfo>
)
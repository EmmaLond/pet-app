package com.example.petapp

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithPets(
    @Embedded val user: Account,
    @Relation(
        parentColumn = "userId",
        entityColumn = "petId",
        associateBy = Junction(UserPetCrossRef::class)
    )
    val pets: List<PetInfo>
)
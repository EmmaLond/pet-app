package com.example.petapp
// package com.example.petapp.Room_Database

import android.R
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    //user methods
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<Account>>

    @Query("SELECT password FROM users WHERE email = :email")
    suspend fun getPassword(email: String): String?

    @Insert
    suspend fun insert(user: Account)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Query("SELECT userId FROM users WHERE email = :email")
    suspend fun getUserId(email: String): Int

    //pet methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetInfo): Long

    @Query("SELECT * FROM pets WHERE userId = :userId")
    fun getPetsForUser(userId: Int): kotlinx.coroutines.flow.Flow<List<PetInfo>>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithPets(userId: Int): List<UserWithPets>
}
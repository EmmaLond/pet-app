package com.example.petapp

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
    //method that adds a pet into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetInfo): Long

    //method that retrieves all pets associated with the selected userID
    @Query("SELECT * FROM pets WHERE userId = :userId")
    fun getPetsForUser(userId: Int): Flow<List<PetInfo>>

    @Query("SELECT * FROM pets WHERE petId = :petId")
    fun getPet(petId: Int): Flow<PetInfo?>

    @Query("DELETE FROM pets WHERE petId = :petId")
    suspend fun removePet(petId: Int)

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithPets(userId: Int): UserWithPets

    // Log methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: Log): Long

    @Query("SELECT * FROM log WHERE petId = :petId ORDER BY timeOccurred DESC")
    fun getLog(petId: Int): Flow<List<Log>>
}
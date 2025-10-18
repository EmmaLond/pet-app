package com.example.petapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<Account>>

    @Insert
    suspend fun insert(user: Account)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
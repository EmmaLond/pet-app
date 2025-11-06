package com.example.petapp
// package com.example.petapp.Room_Database

import android.R
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<Account>>

    @Query("SELECT password FROM users WHERE email = :email")
    suspend fun getPassword(email: String): String?

    @Insert
    suspend fun insert(user: Account)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
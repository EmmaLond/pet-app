package com.example.petapp

import androidx.room.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters

@Database(entities = [Account::class, PetInfo::class, Log::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
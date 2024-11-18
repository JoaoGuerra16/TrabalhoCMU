package com.example.trabalhocmu.room.RoomDatabase

import android.content.Context
import androidx.room.Room
import com.example.trabalhocmu.room.entity.AppDatabase

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val tempDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            database = tempDatabase
            tempDatabase
        }
    }
}

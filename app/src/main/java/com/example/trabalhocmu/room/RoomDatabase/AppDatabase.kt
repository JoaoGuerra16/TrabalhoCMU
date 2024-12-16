package com.example.trabalhocmu.room.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trabalhocmu.room.Dao.RideDao
import com.example.trabalhocmu.room.Dao.UserDao

@Database(entities = [User::class, Ride::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rideDao(): RideDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ) .fallbackToDestructiveMigration() // Apaga e recria o banco automaticamente
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

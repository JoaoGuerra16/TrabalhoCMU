package com.example.trabalhocmu.room.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.trabalhocmu.room.Dao.RideDao
import com.example.trabalhocmu.room.Dao.RideParticipantDao
import com.example.trabalhocmu.room.Dao.RideRequestDao
import com.example.trabalhocmu.room.Dao.UserDao


@Database(entities = [User::class, Ride::class, RideParticipant::class, RideRequest::class], version = 13, exportSchema = false)
@TypeConverters(BooleanConverter::class) // Adicione o TypeConverter aqui
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rideDao(): RideDao
    abstract fun rideParticipantDao(): RideParticipantDao
    abstract fun rideRequestDao(): RideRequestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "RideMate"
                )
                    .fallbackToDestructiveMigration() // Atualize automaticamente o banco
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}

package com.example.trabalhocmu.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabalhocmu.room.entity.Ride


@Dao
interface RideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: Ride)

    @Query("SELECT * FROM rides WHERE id = :id")
    suspend fun getRideById(id: Int): Ride?

    @Query("SELECT * FROM rides")
    suspend fun getAllRides(): List<Ride>
}

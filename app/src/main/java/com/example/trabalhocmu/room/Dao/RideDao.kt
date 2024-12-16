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

    @Query("SELECT * FROM rides WHERE ownerEmail != :excludeEmail")
    suspend fun getAvailableRides(excludeEmail: String): List<Ride>

    @Query("SELECT * FROM rides WHERE id = :rideId")
    suspend fun getRideById(rideId: Int): Ride?

    @Query("UPDATE rides SET availablePlaces = :newAvailablePlaces WHERE id = :rideId")
    suspend fun updateAvailablePlaces(rideId: Int, newAvailablePlaces: Int)
}

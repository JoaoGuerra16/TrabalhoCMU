package com.example.trabalhocmu.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabalhocmu.room.entity.Ride


@Dao
interface RideDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRides(rides: List<Ride>)

    @Query("DELETE FROM rides")
    suspend fun clearRides()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: Ride): Long

    @Query("SELECT * FROM rides WHERE ownerEmail != :excludeEmail")
    suspend fun getAvailableRides(excludeEmail: String): List<Ride>

    @Query("SELECT * FROM rides WHERE id = :rideId")
    suspend fun getRideById(rideId: Int): Ride?

    @Query("UPDATE rides SET availablePlaces = :newAvailablePlaces WHERE id = :rideId")
    suspend fun updateAvailablePlaces(rideId: Int, newAvailablePlaces: Int)

    @Query("SELECT * FROM rides WHERE ownerEmail = :ownerEmail")
    suspend fun getRidesByOwnerEmail(ownerEmail: String): List<Ride>

    @Query("DELETE FROM rides WHERE id = :rideId")
    suspend fun deleteRideById(rideId: Int)

    @Query("UPDATE rides SET status = :status WHERE id = :rideId")
    suspend fun updateRideStatus(rideId: Int, status: String)

    @Query("SELECT * FROM rides WHERE status = :status")
    suspend fun getRidesByStatus(status: String): List<Ride>
}


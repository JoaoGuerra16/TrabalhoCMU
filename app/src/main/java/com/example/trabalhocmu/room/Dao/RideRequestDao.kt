package com.example.trabalhocmu.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabalhocmu.room.entity.RideRequest

@Dao
interface RideRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRideRequest(request: RideRequest): Long

    @Query("SELECT * FROM ride_requests WHERE rideId = :rideId")
    suspend fun getRequestsByRide(rideId: Int): List<RideRequest>

    @Query("SELECT * FROM ride_requests WHERE requesterEmail = :email")
    suspend fun getRequestsByUser(email: String): List<RideRequest>

    @Query("UPDATE ride_requests SET status = :status WHERE id = :requestId")
    suspend fun updateRequestStatus(requestId: Int, status: String)

    @Query("SELECT * FROM ride_requests WHERE id = :requestId")
    suspend fun getRequestById(requestId: Int): RideRequest?
}

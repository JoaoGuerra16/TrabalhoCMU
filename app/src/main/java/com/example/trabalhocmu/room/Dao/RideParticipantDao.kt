package com.example.trabalhocmu.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabalhocmu.room.entity.RideParticipant

@Dao
interface RideParticipantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRideParticipant(participant: RideParticipant)

    @Query("SELECT * FROM RideParticipant WHERE rideId = :rideId")
    suspend fun getParticipantsByRide(rideId: Int): List<RideParticipant>

    @Query("SELECT * FROM RideParticipant WHERE userEmail = :userEmail AND role = :role")
    suspend fun getRidesByUserAndRole(userEmail: String, role: String): List<RideParticipant>

    @Query("SELECT * FROM RideParticipant WHERE rideId = :rideId AND role = 'PASSENGER'")
    suspend fun getPassengersByRide(rideId: Int): List<RideParticipant>

    @Query("DELETE FROM RideParticipant WHERE rideId = :rideId AND userEmail = :userEmail")
    suspend fun deleteParticipant(rideId: Int, userEmail: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipants(participants: List<RideParticipant>)

    @Query("DELETE FROM RideParticipant")
    suspend fun clearAllParticipants()


    @Query("SELECT * FROM RideParticipant WHERE rideId = :rideId AND userEmail = :userEmail")
    suspend fun getParticipant(rideId: Int, userEmail: String): RideParticipant?

    @Query("UPDATE RideParticipant SET hasRated = 1 WHERE rideId = :rideId AND userEmail = :userEmail")
    suspend fun markAsRated(rideId: Int, userEmail: String)

}

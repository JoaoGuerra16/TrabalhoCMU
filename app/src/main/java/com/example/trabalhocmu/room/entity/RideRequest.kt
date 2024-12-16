package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ride_requests")
data class RideRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rideId: Int, // Refere-se ao Ride.id
    val requesterEmail: String, // Email do usuário que está pedindo para participar
    val status: String = "PENDING" // Status: "PENDING", "ACCEPTED", "REJECTED"
)

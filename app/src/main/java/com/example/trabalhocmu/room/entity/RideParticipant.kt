package com.example.trabalhocmu.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["rideId", "userEmail"])
data class RideParticipant(
    val rideId: Int, // Refere-se ao Ride.id
    val userEmail: String, // Refere-se ao User.email
    val role: String // "DRIVER" ou "PASSENGER"
)

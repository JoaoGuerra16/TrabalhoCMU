package com.example.trabalhocmu.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["rideId", "userEmail"])
data class RideParticipant(
    val id: Int = 0,
    val rideId: Int = 0,
    val userEmail: String = "",
    val role: String = "",
    val hasRated: Boolean = false
) {
    // Construtor sem argumentos para Firestore
    constructor() : this(0, 0, "", "", false)
}
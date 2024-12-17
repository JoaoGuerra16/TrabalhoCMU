package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ride_requests")
data class RideRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rideId: Int = 0,
    val requesterEmail: String = "",
    val status: String = "PENDING"
) {
    // Construtor sem argumentos para Firestore
    constructor() : this(0, 0, "", "PENDING")
}
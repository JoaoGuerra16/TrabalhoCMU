package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


import androidx.annotation.Keep

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startingPoint: String = "",
    val finalDestination: String = "",
    val startingDate: String = "",
    val executedArrival: String = "",
    val availablePlaces: Int = 0,
    val isPetsAllowed: Boolean = false,
    val isBaggageAllowed: Boolean = false,
    val isSmokingAllowed: Boolean = false,
    val ownerEmail: String = "" // Associar o condutor (User.email)
)

package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startingPoint: String,
    val finalDestination: String,
    val startingDate: String,
    val executedArrival: String,
    val availablePlaces: Int,
    val isPetsAllowed: Boolean,
    val isBaggageAllowed: Boolean,
    val isSmokingAllowed: Boolean
)
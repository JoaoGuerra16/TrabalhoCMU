package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ride_requests")
data class RideRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rideId: Int = 0,
    val requesterEmail: String = "",
    val status: String = "PENDING",
    var isNormalRouteInt: Int = 1, // Armazena como Int diretamente
    val pickupLocation: String? = null,
    val dropoffLocation: String? = null
) {
    // Getter e Setter para converter Int em Boolean
    var isNormalRoute: Boolean
        get() = isNormalRouteInt == 1
        set(value) {
            isNormalRouteInt = if (value) 1 else 0
        }

    // Construtor sem argumentos para a Firebase deserializar
    constructor() : this(0, 0, "", "PENDING", 1, null, null)
}
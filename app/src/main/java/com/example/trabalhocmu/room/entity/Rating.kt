package com.example.trabalhocmu.room.entity

data class Rating(
    val rideId: Int = 0, // ID da ride associada
    val driverEmail: String = "", // Email do condutor
    val passengerEmail: String = "", // Email do passageiro
    val rating: Int = 0 // Nota dada (1-5)
) {
    constructor() : this(0, "", "", 0) // Construtor vazio para Firestore
}

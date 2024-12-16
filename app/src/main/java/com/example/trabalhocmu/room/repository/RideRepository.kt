package com.example.trabalhocmu.room.repository

import android.content.Context
import android.util.Log
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.entity.RideParticipant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class RideRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun createRide(ride: Ride): Boolean {
        return try {
            // 1. Salvar a ride no Room
            db.rideDao().insertRide(ride)

            // 2. Adicionar o condutor como participante (DRIVER)
            val driverParticipant = RideParticipant(
                rideId = ride.id,
                userEmail = ride.ownerEmail,
                role = "DRIVER"
            )
            db.rideParticipantDao().insertRideParticipant(driverParticipant)

            // 3. Salvar a ride e o condutor no Firestore
            val firestoreRideRef = firestore.collection("rides").add(ride).await()
            firestore.collection("rideParticipants").add(driverParticipant).await()

            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao criar ride: ${e.message}")
            false
        }
    }

    suspend fun acceptRide(rideId: Int, userEmail: String): Boolean {
        return try {
            // 1. Adicionar o passageiro como participante
            val passengerParticipant = RideParticipant(
                rideId = rideId,
                userEmail = userEmail,
                role = "PASSENGER"
            )
            db.rideParticipantDao().insertRideParticipant(passengerParticipant)
            firestore.collection("rideParticipants").add(passengerParticipant).await()

            // 2. Atualizar o número de lugares disponíveis
            val ride = db.rideDao().getRideById(rideId)
            if (ride != null && ride.availablePlaces > 0) {
                val newAvailablePlaces = ride.availablePlaces - 1
                db.rideDao().updateAvailablePlaces(rideId, newAvailablePlaces)
                firestore.collection("rides").document("$rideId")
                    .update("availablePlaces", newAvailablePlaces).await()
            }

            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao aceitar ride: ${e.message}")
            false
        }
    }
    suspend fun getParticipantsByRide(rideId: Int): List<RideParticipant> {
        return db.rideParticipantDao().getParticipantsByRide(rideId)
    }



    suspend fun getAvailableRides(excludeEmail: String): List<Ride> {
        return db.rideDao().getAvailableRides(excludeEmail)
    }


    suspend fun addParticipantToRide(participant: RideParticipant): Boolean {
        return try {
            db.rideParticipantDao().insertRideParticipant(participant)
            firestore.collection("rideParticipants").add(participant).await()
            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao adicionar participante: ${e.message}")
            false
        }
    }



    suspend fun updateAvailablePlaces(rideId: Int, newAvailablePlaces: Int): Boolean {
        return try {
            db.rideDao().updateAvailablePlaces(rideId, newAvailablePlaces)
            firestore.collection("rides").document("$rideId")
                .update("availablePlaces", newAvailablePlaces).await()
            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao atualizar lugares disponíveis: ${e.message}")
            false
        }
    }

    suspend fun getRideById(rideId: Int): Ride? {
        return db.rideDao().getRideById(rideId)
    }
}
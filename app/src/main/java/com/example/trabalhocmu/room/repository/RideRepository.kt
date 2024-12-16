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

    suspend fun createRide(ride: Ride, participants: List<RideParticipant>): Boolean {
        return try {
            // Salvar no Room
            db.rideDao().insertRide(ride)
            participants.forEach { db.rideParticipantDao().insertRideParticipant(it) }

            // Salvar no Firebase Firestore
            firestore.collection("rides").add(ride).await()
            participants.forEach {
                firestore.collection("rideParticipants").add(it).await()
            }

            true
        } catch (e: Exception) {
            false
        }
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
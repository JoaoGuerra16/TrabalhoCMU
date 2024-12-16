package com.example.trabalhocmu.room.repository

import android.content.Context
import android.util.Log
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.entity.RideParticipant
import com.example.trabalhocmu.room.entity.RideRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class RideRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val firestore = FirebaseFirestore.getInstance()


    suspend fun createRide(ride: Ride): Boolean {
        return try {
            // 1. Salvar a ride no Room e obter o ID gerado
            val rideId = db.rideDao().insertRide(ride).toInt()

            // 2. Criar uma cópia da ride com o ID gerado
            val rideWithId = ride.copy(id = rideId)

            // 3. Salvar no Firestore com o ID correto
            firestore.collection("rides").document(rideId.toString()).set(rideWithId).await()


            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao criar ride: ${e.message}")
            false
        }
    }



    suspend fun acceptRide(rideId: Int, userEmail: String): Boolean {
        return try {
            // 1. Obter todos os participantes da ride
            val participants = db.rideParticipantDao().getParticipantsByRide(rideId)
            val ride = db.rideDao().getRideById(rideId)

            // 2. Verificar se ainda há lugares disponíveis
            val passengerCount = participants.count { it.role == "PASSENGER" }
            if (ride != null && passengerCount < ride.availablePlaces) {
                // 3. Adicionar o passageiro como participante
                val passengerParticipant = RideParticipant(
                    rideId = rideId,
                    userEmail = userEmail,
                    role = "PASSENGER"
                )
                db.rideParticipantDao().insertRideParticipant(passengerParticipant)
                firestore.collection("rideParticipants").add(passengerParticipant).await()

                // 4. Atualizar o número de lugares disponíveis no Room e Firestore
                val newAvailablePlaces = ride.availablePlaces - 1
                db.rideDao().updateAvailablePlaces(rideId, newAvailablePlaces)
                firestore.collection("rides").document("$rideId")
                    .update("availablePlaces", newAvailablePlaces).await()

                true
            } else {
                Log.e("RideRepository", "Ride is full. Cannot accept more participants.")
                false
            }
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

    suspend fun getRidesByRole(userEmail: String, role: String): List<Ride> {
        val participants = db.rideParticipantDao().getRidesByUserAndRole(userEmail, role)
        return participants.mapNotNull { db.rideDao().getRideById(it.rideId) }
    }


    suspend fun getRideById(rideId: Int): Ride? {
        return db.rideDao().getRideById(rideId)
    }

    suspend fun getRidesByOwnerEmail(ownerEmail: String): List<Ride> {
        return db.rideDao().getRidesByOwnerEmail(ownerEmail)
    }


    suspend fun createRideRequest(request: RideRequest): Boolean {
        return try {
            // 1. Salvar o pedido no Room e obter o ID gerado
            val requestId = db.RideRequestDao().insertRideRequest(request).toInt()

            // 2. Criar uma cópia do pedido com o ID gerado
            val requestWithId = request.copy(id = requestId)

            // 3. Salvar no Firestore com o ID gerado pelo Room
            firestore.collection("rideRequests").document(requestId.toString()).set(requestWithId).await()

            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao criar pedido: ${e.message}")
            false
        }
    }



    suspend fun getRequestsByRide(rideId: Int): List<RideRequest> {
        return db.RideRequestDao().getRequestsByRide(rideId)
    }

    suspend fun getRequestsByUser(email: String): List<RideRequest> {
        return db.RideRequestDao().getRequestsByUser(email)
    }

    suspend fun updateRequestStatus(requestId: Int, status: String): Boolean {
        return try {
            // Atualiza o status no Room
            db.RideRequestDao().updateRequestStatus(requestId, status)

            // Atualiza o status no Firestore
            firestore.collection("rideRequests")
                .document(requestId.toString())
                .update("status", status)
                .await()

            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao atualizar status do pedido: ${e.message}")
            false
        }
    }

    suspend fun getRequestById(requestId: Int): RideRequest? {
        return db.RideRequestDao().getRequestById(requestId)
    }
    suspend fun updateRequestStatusInFirestore(firestoreId: String, status: String): Boolean {
        return try {
            firestore.collection("rideRequests").document(firestoreId)
                .update("status", status).await()
            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao atualizar status do pedido: ${e.message}")
            false
        }
    }

    suspend fun removeParticipant(rideId: Int, userEmail: String): Boolean {
        return try {
            // Remover o participante no Room
            db.rideParticipantDao().deleteParticipant(rideId, userEmail)

            // Remover o participante no Firestore
            val participantQuery = firestore.collection("rideParticipants")
                .whereEqualTo("rideId", rideId)
                .whereEqualTo("userEmail", userEmail)
                .get().await()

            for (document in participantQuery.documents) {
                document.reference.delete().await()
            }

            true
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao remover participante: ${e.message}")
            false
        }
    }
    suspend fun incrementAvailablePlaces(rideId: Int): Boolean {
        return try {
            val ride = db.rideDao().getRideById(rideId)
            if (ride != null) {
                val newAvailablePlaces = ride.availablePlaces + 1
                db.rideDao().updateAvailablePlaces(rideId, newAvailablePlaces)

                // Atualizar no Firestore
                firestore.collection("rides").document(rideId.toString())
                    .update("availablePlaces", newAvailablePlaces).await()

                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("RideRepository", "Erro ao incrementar lugares disponíveis: ${e.message}")
            false
        }
    }


}

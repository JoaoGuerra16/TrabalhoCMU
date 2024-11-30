package com.example.trabalhocmu.room.repository

import android.content.Context
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.room.entity.Ride
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class RideRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun createRide(ride: Ride): Boolean {
        return try {
            // Salvar no Room
            db.rideDao().insertRide(ride)

            // Salvar no Firebase Firestore
            firestore.collection("rides").add(ride).await()

            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getRides(): List<Ride> {
        return db.rideDao().getAllRides()
    }
}

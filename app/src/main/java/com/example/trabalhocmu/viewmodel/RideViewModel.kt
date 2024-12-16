package com.example.trabalhocmu.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.entity.RideParticipant
import com.example.trabalhocmu.room.repository.RideRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RideViewModel(context: Context) : ViewModel() {

    private val rideRepository = RideRepository(context)

    private val _rideState = MutableStateFlow<RideState>(RideState.Idle)
    val rideState: StateFlow<RideState> = _rideState

    fun createRide(
        startingPoint: String,
        finalDestination: String,
        startingDate: String,
        executedArrival: String,
        availablePlaces: Int,
        isPetsAllowed: Boolean,
        isBaggageAllowed: Boolean,
        isSmokingAllowed: Boolean,
        ownerEmail: String, // Email do condutor
        passengers: List<String> // Emails dos passageiros
    ) {
        viewModelScope.launch {
            _rideState.value = RideState.Loading

            val ride = Ride(
                startingPoint = startingPoint,
                finalDestination = finalDestination,
                startingDate = startingDate,
                executedArrival = executedArrival,
                availablePlaces = availablePlaces,
                isPetsAllowed = isPetsAllowed,
                isBaggageAllowed = isBaggageAllowed,
                isSmokingAllowed = isSmokingAllowed,
                ownerEmail = ownerEmail
            )

            // Adicione o condutor como participante
            val participants = mutableListOf<RideParticipant>()
            participants.add(
                RideParticipant(
                    rideId = ride.id,
                    userEmail = ownerEmail,
                    role = "DRIVER"
                )
            )

            // Adicione os passageiros como participantes
            passengers.forEach { email ->
                participants.add(
                    RideParticipant(
                        rideId = ride.id,
                        userEmail = email,
                        role = "PASSENGER"
                    )
                )
            }

            val success = rideRepository.createRide(ride, participants)
            _rideState.value = if (success) {
                RideState.Success
            } else {
                RideState.Error("Erro ao criar ride.")
            }

        }


    }

    fun getAvailableRides(): Flow<List<Ride>> = flow {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            val availableRides = rideRepository.getAvailableRides(excludeEmail = currentUserEmail)
            emit(availableRides)
        } else {
            emit(emptyList()) // Caso o utilizador não esteja logado
        }
    }

    fun acceptRide(rideId: Int) {
        viewModelScope.launch {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (currentUserEmail != null) {
                val ride = rideRepository.getRideById(rideId)
                if (ride != null && ride.availablePlaces > 0) {
                    // Adicionar o utilizador como passageiro
                    val participant = RideParticipant(
                        rideId = rideId,
                        userEmail = currentUserEmail,
                        role = "PASSENGER"
                    )
                    val success = rideRepository.addParticipantToRide(participant)

                    // Atualizar lugares disponíveis
                    if (success) {
                        rideRepository.updateAvailablePlaces(rideId, ride.availablePlaces - 1)
                    }
                }
            }
        }
    }
}

sealed class RideState {
    object Idle : RideState()
    object Loading : RideState()
    object Success : RideState()
    data class Error(val message: String) : RideState()
}

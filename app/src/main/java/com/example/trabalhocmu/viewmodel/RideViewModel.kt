package com.example.trabalhocmu.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.entity.RideParticipant
import com.example.trabalhocmu.room.entity.RideRequest
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
        ownerEmail: String,
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

            val success = rideRepository.createRide(ride)
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
            emit(availableRides.filter { it.availablePlaces > 0 })
        } else {
            emit(emptyList()) // Caso o utilizador não esteja logado
        }
    }



    fun getParticipants(rideId: Int): Flow<List<RideParticipant>> {
        return flow {
            val participants = rideRepository.getParticipantsByRide(rideId)
            emit(participants)
        }
    }

    fun getRideById(rideId: Int): Flow<Ride?> {
        return flow {
            val ride = rideRepository.getRideById(rideId)
            emit(ride)
        }
    }

    fun getRidesAsDriver(): Flow<List<Ride>> = flow {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            val rides = rideRepository.getRidesByOwnerEmail(currentUserEmail)
                .filter { it.status == "PLANNED" || it.status == "IN_PROGRESS" } // Filtra apenas as rides ativas
            emit(rides)
        } else {
            emit(emptyList())
        }
    }

    fun getRidesAsPassenger(): Flow<List<Ride>> = flow {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            val rides = rideRepository.getRidesByRole(currentUserEmail, "PASSENGER")
                .filter { it.status == "PLANNED" || it.status == "IN_PROGRESS" } // Filtra apenas as rides ativas
            emit(rides)
        } else {
            emit(emptyList())
        }
    }
    fun getParticipantsWithDetails(rideId: Int): Flow<List<Pair<RideParticipant, String>>> = flow {
        val participants = rideRepository.getParticipantsByRide(rideId)
        val requests = rideRepository.getRequestsByRide(rideId)

        val participantDetails = participants.map { participant ->
            val request = requests.find { it.requesterEmail == participant.userEmail }
            val routeInfo = if (request?.isNormalRoute == true) {
                "Normal Route"
            } else {
                "Pickup: ${request?.pickupLocation ?: "N/A"} | Dropoff: ${request?.dropoffLocation ?: "N/A"}"
            }
            Pair(participant, routeInfo)
        }

        emit(participantDetails)
    }

    fun requestToJoinRide(
        rideId: Int,
        isNormalRoute: Boolean,
        pickupLocation: String? = null,
        dropoffLocation: String? = null
    ) {
        viewModelScope.launch {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (currentUserEmail != null) {
                val request = RideRequest(
                    rideId = rideId,
                    requesterEmail = currentUserEmail,
                    status = "PENDING",
                    isNormalRouteInt = if (isNormalRoute) 1 else 0, // Aqui a correção
                    pickupLocation = pickupLocation,
                    dropoffLocation = dropoffLocation
                )
                rideRepository.createRideRequest(request)
            }
        }
    }


    fun getRequestsForRide(rideId: Int): Flow<List<RideRequest>> = flow {
        emit(rideRepository.getRequestsByRide(rideId))
    }

    fun respondToRequest(requestId: Int, status: String) {
        viewModelScope.launch {
            val request = rideRepository.getRequestById(requestId)
            Log.d("RideViewModel", "Request ID: $requestId, isNormalRoute: ${request?.isNormalRoute}")
            if (request != null) {
                // Atualizar o estado no Room
                rideRepository.updateRequestStatus(requestId, status)

                // Atualizar o estado no Firestore usando o ID do documento
                val firestoreId = request.id.toString() // Certifique-se de que isso é o ID do Firestore
                rideRepository.updateRequestStatusInFirestore(firestoreId, status)

                if (status == "ACCEPTED") {
                    val participant = RideParticipant(
                        rideId = request.rideId,
                        userEmail = request.requesterEmail,
                        role = "PASSENGER"
                    )
                    rideRepository.addParticipantToRide(participant)


                }
            }
        }
    }
    fun removePassenger(rideId: Int, userEmail: String) {
        viewModelScope.launch {
            val success = rideRepository.removeParticipant(rideId, userEmail)
            if (success) {
                rideRepository.incrementAvailablePlaces(rideId)
            } else {
                Log.e("RideViewModel", "Erro ao remover passageiro.")
            }
        }
    }
    fun leaveRide(rideId: Int) {
        viewModelScope.launch {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (currentUserEmail != null) {
                val success = rideRepository.removeParticipant(rideId, currentUserEmail)
                if (success) {
                    rideRepository.incrementAvailablePlaces(rideId)
                } else {
                    Log.e("RideViewModel", "Erro ao sair da ride.")
                }
            }
        }
    }

    fun syncAllData() {
        viewModelScope.launch {
            rideRepository.syncAllDataFromFirestoreToLocal()
        }
    }


    fun cancelRide(rideId: Int) {
        viewModelScope.launch {
            val success = rideRepository.cancelRide(rideId)
            if (success) {
                Log.d("RideViewModel", "Ride cancelada com sucesso.")
            } else {
                Log.e("RideViewModel", "Erro ao cancelar a ride.")
            }
        }
    }
    fun startRide(rideId: Int) {
        viewModelScope.launch {
            rideRepository.startRide(rideId)
        }
    }

    fun completeRide(rideId: Int) {
        viewModelScope.launch {
            rideRepository.completeRide(rideId)
        }
    }

    fun getCompletedRides(): Flow<List<Ride>> = flow {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            val rides = rideRepository.getCompletedRidesForUser(currentUserEmail)
            emit(rides)
        } else {
            emit(emptyList())
        }
    }


}


sealed class RideState {
    object Idle : RideState()
    object Loading : RideState()
    object Success : RideState()
    data class Error(val message: String) : RideState()
}

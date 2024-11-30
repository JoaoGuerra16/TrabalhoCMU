package com.example.trabalhocmu.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.repository.RideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        isSmokingAllowed: Boolean
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
                isSmokingAllowed = isSmokingAllowed
            )

            val success = rideRepository.createRide(ride)
            _rideState.value = if (success) {
                RideState.Success
            } else {
                RideState.Error("Erro ao criar ride.")
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

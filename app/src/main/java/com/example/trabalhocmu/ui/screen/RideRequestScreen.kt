package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.viewmodel.RideViewModel

@Composable
fun RideRequestScreen(
    rideId: Int,
    rideViewModel: RideViewModel,
    navController: NavController
) {
    val ride by rideViewModel.getRideById(rideId).collectAsState(initial = null)

    var isNormalRoute by remember { mutableStateOf(true) }
    var pickupLocation by remember { mutableStateOf("") }
    var dropoffLocation by remember { mutableStateOf("") }

    ride?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Ride Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text("From: ${it.startingPoint}")
            Text("To: ${it.finalDestination}")
            Text("Available Places: ${it.availablePlaces}")

            Spacer(modifier = Modifier.height(16.dp))
            Text("Choose your request option:")

            Row(modifier = Modifier.padding(top = 8.dp)) {
                RadioButton(selected = isNormalRoute, onClick = { isNormalRoute = true })
                Text("Normal Route")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = !isNormalRoute, onClick = { isNormalRoute = false })
                Text("Custom Pickup/Dropoff")
            }

            if (!isNormalRoute) {
                TextField(
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it },
                    label = { Text("Pickup Location") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = dropoffLocation,
                    onValueChange = { dropoffLocation = it },
                    label = { Text("Dropoff Location") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                rideViewModel.requestToJoinRide(
                    rideId = rideId,
                    isNormalRoute = isNormalRoute,
                    pickupLocation = if (!isNormalRoute) pickupLocation else null,
                    dropoffLocation = if (!isNormalRoute) dropoffLocation else null
                )
                navController.popBackStack() // Volta à tela anterior
            }) {
                Text("Send Request")
            }
        }
    } ?: run {
        CircularProgressIndicator()
    }
}

package com.example.trabalhocmu.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.viewmodel.RideViewModel

@Composable
fun ManagePassengersScreen(
    rideId: Int,
    rideViewModel: RideViewModel,
    navController: NavController
) {
    val participants by rideViewModel.getParticipants(rideId).collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manage Passengers", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(participants.filter { it.role == "PASSENGER" }) { passenger ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = passenger.userEmail)
                    Button(onClick = {
                        rideViewModel.removePassenger(rideId, passenger.userEmail)
                    }) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}

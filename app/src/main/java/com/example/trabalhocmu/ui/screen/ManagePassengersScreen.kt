package com.example.trabalhocmu.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    // Sincronizar dados ao abrir a tela
    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }
    val participantsWithDetails by rideViewModel.getParticipantsWithDetails(rideId)
        .collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manage Passengers", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(participantsWithDetails) { (participant, routeInfo) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Email: ${participant.userEmail}", fontWeight = FontWeight.Bold)
                        Text("Route Info: $routeInfo")

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { rideViewModel.removePassenger(rideId, participant.userEmail) },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }
}
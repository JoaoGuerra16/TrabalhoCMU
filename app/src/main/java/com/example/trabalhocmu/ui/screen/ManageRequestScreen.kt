package com.example.trabalhocmu.ui.screen

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.viewmodel.RideViewModel


@Composable
fun ManageRequestsScreen(navController: NavController, rideViewModel: RideViewModel, rideId: Int) {
    val requests by rideViewModel.getPendingRequestsForRide(rideId).collectAsState(initial = emptyList())
    // Sincronizar dados ao abrir a tela
    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manage Requests", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(requests) { request ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Requester: ${request.requesterEmail}")
                        Text("Status: ${request.status}")
                        if (request.isNormalRoute) {
                            Text("Route: Normal Route")
                        } else {
                            Text("Pickup: ${request.pickupLocation ?: "Not specified"}")
                            Text("Dropoff: ${request.dropoffLocation ?: "Not specified"}")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = {
                                rideViewModel.respondToRequest(
                                    request.id,
                                    "ACCEPTED"
                                )
                            }) {
                                Text("Accept")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                rideViewModel.respondToRequest(
                                    request.id,
                                    "REJECTED"
                                )
                            }) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}
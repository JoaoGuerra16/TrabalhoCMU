package com.example.trabalhocmu.ui.screen

import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.API.models.MapViewComposable
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ManageRequestsScreen(navController: NavController, rideViewModel: RideViewModel, rideId: Int) {
    val requests by rideViewModel.getRequestsForRide(rideId).collectAsState(initial = emptyList())
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

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(onClick = { rideViewModel.respondToRequest(request.id, "ACCEPTED") }) {
                                Text("Accept")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { rideViewModel.respondToRequest(request.id, "REJECTED") }) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}

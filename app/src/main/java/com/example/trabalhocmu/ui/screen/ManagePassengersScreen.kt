package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel

@OptIn(ExperimentalMaterial3Api::class)
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



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )

        SidebarScaffold(navController = navController) { paddingValues ->
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Manage Passengers",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent,
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp)
                    ) {
                        LazyColumn {
                            items(participantsWithDetails) { (participant, routeInfo) ->
                                PassengerCard(
                                    participantEmail = participant.userEmail,
                                    routeInfo = routeInfo,
                                    onRemove = {
                                        rideViewModel.removePassenger(rideId, participant.userEmail)
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun PassengerCard(
    participantEmail: String,
    routeInfo: String,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Email: $participantEmail", fontWeight = FontWeight.Bold)
            Text("Route Info: $routeInfo", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRemove,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Remove", color = Color.White)
            }
        }
    }
}
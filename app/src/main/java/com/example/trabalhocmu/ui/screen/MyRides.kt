package com.example.trabalhocmu.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import androidx.compose.ui.res.stringResource
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRides(navController: NavController, rideViewModel: RideViewModel) {
    val ridesAsDriver by rideViewModel.getRidesAsDriver().collectAsState(initial = emptyList())
    val ridesAsPassenger by rideViewModel.getRidesAsPassenger().collectAsState(initial = emptyList())

    // Scaffold para gerenciar a estrutura de layout com um botão flutuante
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Rides",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily
                    )
                },
                actions = {
                    // Botão no canto superior direito
                    IconButton(onClick = {
                        navController.navigate("Create Ride")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_ride),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF454B60)
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Giving Rides", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {
                    items(ridesAsDriver) { ride ->
                        RideCard(
                            ride = ride,
                            role = "Giving Ride",
                            onDetailsClick = { navController.navigate("RideDetails/${ride.id}") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Taking Rides", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {
                    items(ridesAsPassenger) { ride ->
                        RideCard(
                            ride = ride,
                            role = "Taking Ride",
                            onDetailsClick = { navController.navigate("RideDetails/${ride.id}") }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun RideCard(ride: Ride, role: String, onDetailsClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Role: $role", fontWeight = FontWeight.Bold)
            Text("From: ${ride.startingPoint}")
            Text("To: ${ride.finalDestination}")
            Text("Date: ${ride.startingDate}")
            Text("Available Seats: ${ride.availablePlaces}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDetailsClick, modifier = Modifier.align(Alignment.End)) {
                Text("Details")
            }
        }
    }
}


// Data class para representar uma viagem
data class RideTeste(
    val from: String,
    val to: String,
    val availableSeats: Int,
    val startTime: String,
    val arrivalTime: String,
    val date: LocalDate,
    val isGivingRide: Boolean
)

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.RideViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter




@Composable
fun FindRidesScreen(navController: NavController, rideViewModel: RideViewModel) {
    val rides by rideViewModel.getAvailableRides().collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Available Rides", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(rides) { ride ->
                RideCard(
                    ride = ride,
                    onDetailsClick = {
                        navController.navigate("RideDetails/${ride.id}")
                    },
                    onAcceptClick = {
                        rideViewModel.acceptRide(ride.id)
                    }
                )
            }
        }
    }
}

@Composable
fun RideCard(ride: Ride, onDetailsClick: () -> Unit, onAcceptClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("From: ${ride.startingPoint}")
            Text("To: ${ride.finalDestination}")
            Text("Date: ${ride.startingDate}")
            Text("Available Seats: ${ride.availablePlaces}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onDetailsClick) {
                    Text("Details")
                }
                Button(onClick = onAcceptClick) {
                    Text("Accept Ride")
                }
            }
        }
    }
}




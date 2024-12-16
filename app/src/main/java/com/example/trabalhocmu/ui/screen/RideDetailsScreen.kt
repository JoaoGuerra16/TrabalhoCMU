package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel

@Composable
fun RideDetailsScreen(navController: NavController, rideId: Int, rideViewModel: RideViewModel) {
    val participants by rideViewModel.getParticipants(rideId).collectAsState(initial = emptyList())
    val ride = rideViewModel.getRideById(rideId).collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ride.value?.let {
            Text("Ride Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Text("From: ${it.startingPoint}")
            Text("To: ${it.finalDestination}")
            Text("Date: ${it.startingDate}")
            Text("Available Seats: ${it.availablePlaces}")
            Text("Email do Condutor:${it.ownerEmail} ")
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text("Participants", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        LazyColumn {
            items(participants) { participant ->
                Text("User: ${participant.userEmail}, Role: ${participant.role}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}



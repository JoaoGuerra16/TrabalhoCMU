package com.example.trabalhocmu.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.SidebarScaffold
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.RideViewModel
import kotlin.random.Random


@Composable
fun RidesHistory(navController: NavController, rideViewModel: RideViewModel) {
    // Obter as rides completadas do ViewModel
    val completedRides by rideViewModel.getCompletedRides().collectAsState(initial = emptyList())

    SidebarScaffold(navController = navController) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "Rides History",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Verificar se há rides completadas
                if (completedRides.isEmpty()) {
                    Text(
                        text = "No completed rides available.",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn {
                        items(completedRides) { ride ->
                            RidesHistoryItem(
                                from = ride.startingPoint,
                                to = ride.finalDestination,
                                startTime = ride.startingDate,
                                availableSeats = ride.availablePlaces,
                                status = ride.status
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão de Voltar
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Back", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun RidesHistoryItem(
    from: String,
    to: String,
    startTime: String,
    availableSeats: Int,
    status: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "From: $from",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )
            Text(
                text = "To: $to",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )
            Text(
                text = "Start Time: $startTime",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "Available Seats: $availableSeats",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "Status: $status",
                color = if (status == "COMPLETED") Color.Green else Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

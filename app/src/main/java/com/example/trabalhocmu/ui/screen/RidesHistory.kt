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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.graphics.Brush
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFFAFAFA),
                            Color(0xFFF0F0F0)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Header Row with From and To
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Location Icon",
                            tint = Color(0xFF3F51B5),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "From: $from",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Destination Icon",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "To: $to",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

                // Status with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (status == "COMPLETED") Icons.Default.CheckCircle else Icons.Default.Close,
                        contentDescription = "Status Icon",
                        tint = if (status == "COMPLETED") Color(0xFF4CAF50) else Color(0xFFF44336),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = status,
                        color = if (status == "COMPLETED") Color(0xFF4CAF50) else Color(0xFFF44336),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Ride Details
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Start Time",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Time Icon",
                            tint = Color(0xFF3F51B5),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = startTime,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Seats Available",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$availableSeats",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3F51B5),
                        fontSize = 15.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}
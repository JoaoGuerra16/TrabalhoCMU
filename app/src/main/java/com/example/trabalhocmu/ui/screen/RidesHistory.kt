package com.example.trabalhocmu.ui.screen

import android.app.DatePickerDialog
import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.SidebarScaffold
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.example.trabalhocmu.viewmodel.RideViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random


@Composable
fun RidesHistory(navController: NavController, viewModel: RideViewModel) {
    val completedRides by viewModel.getCompletedRides().collectAsState(initial = emptyList())
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

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
                                status = ride.status,
                                rideId = ride.id,
                                driverEmail = ride.ownerEmail,
                                navController = navController,
                                rideViewModel = viewModel
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
    status: String,
    rideId: Int,
    driverEmail: String,
    navController: NavController,
    rideViewModel: RideViewModel
) {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

    // Verificar se o passageiro já avaliou
    var hasRated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        hasRated = rideViewModel.hasUserRated(rideId, userEmail)
    }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botão para avaliar
            if (status == "COMPLETED" && !hasRated) {
                Button(
                    onClick = {
                        navController.navigate("RateScreen/$rideId/$driverEmail")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Rate this Ride", color = Color.White)
                }
            } else if (hasRated) {
                Text(
                    text = "You've already rated this ride.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

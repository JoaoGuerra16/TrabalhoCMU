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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRides(navController: NavController, rideViewModel: RideViewModel) {
    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }

    val ridesAsDriver by rideViewModel.getRidesAsDriver().collectAsState(initial = emptyList())
    val ridesAsPassenger by rideViewModel.getRidesAsPassenger().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Substitua pelo nome da imagem
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
                            Text(
                                text = "My Rides",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily,
                                color = Color.Black
                            )
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate("Create Ride") }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Ride",
                                    tint = Color.Black
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent // Garantir fundo transparente
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
                        Spacer(modifier = Modifier.height(8.dp))
                        SectionTitle(title = "Giving Rides")
                        LazyColumn {
                            items(ridesAsDriver) { ride ->
                                RideCard(
                                    ride = ride,
                                    role = "Giving Ride",
                                    onDetailsClick = {
                                        navController.navigate("RideDetails/${ride.id}")
                                    },
                                    onManageRequestsClick = {
                                        navController.navigate("ManageRequests/${ride.id}")
                                    },
                                    onRemoveOrLeaveClick = {
                                        // Remover passageiros (mostrar tela de gerenciamento)
                                        navController.navigate("ManagePassengers/${ride.id}")
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        SectionTitle(title = "Taking Rides")
                        LazyColumn {
                            items(ridesAsPassenger) { ride ->
                                RideCard(
                                    ride = ride,
                                    role = "Taking Ride",
                                    onDetailsClick = {
                                        navController.navigate("RideDetails/${ride.id}")
                                    },
                                    onRemoveOrLeaveClick = {
                                        // Sair da ride
                                        rideViewModel.leaveRide(ride.id)
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
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = PoppinsFamily,
        color = Color(0xFF454B60),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Start
    )
}

@Composable
fun RideCard(
    ride: Ride,
    role: String,
    onDetailsClick: () -> Unit,
    onManageRequestsClick: (() -> Unit)? = null,
    onRemoveOrLeaveClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Role: $role", fontWeight = FontWeight.Bold)
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
                if (role == "Giving Ride" && onManageRequestsClick != null) {
                    Button(onClick = onManageRequestsClick) {
                        Text("Manage Requests")
                    }
                }
                if (onRemoveOrLeaveClick != null) {
                    Button(onClick = onRemoveOrLeaveClick) {
                        Text(if (role == "Giving Ride") "Manage Passengers" else "Leave Ride")
                    }
                }
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

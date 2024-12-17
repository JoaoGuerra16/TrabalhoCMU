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
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRides(navController: NavController, rideViewModel: RideViewModel) {

    val ridesAsDriver by rideViewModel.getRidesAsDriver().collectAsState(initial = emptyList())
    val ridesAsPassenger by rideViewModel.getRidesAsPassenger().collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fundo com imagem
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
                            Text(
                                text = stringResource(id = R.string.my_rides),
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
                                    contentDescription = stringResource(id = R.string.add_ride),
                                    tint = Color.Black
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp)
                    ) {
                        // Giving Rides Section
                        item {
                            SectionTitle(title = stringResource(id = R.string.giving_ride))
                        }

                        if (ridesAsDriver.isNotEmpty()) {
                            items(ridesAsDriver) { ride ->
                                RideCard(
                                    ride = ride,
                                    role = stringResource(id = R.string.giving_ride),
                                    onDetailsClick = {
                                        navController.navigate("RideDetails/${ride.id}")
                                    },
                                    onManageRequestsClick = {
                                        navController.navigate("ManageRequests/${ride.id}")
                                    },
                                    onRemoveOrLeaveClick = {
                                        navController.navigate("ManagePassengers/${ride.id}")
                                    }
                                )
                            }
                        } else {
                            item {
                                Text(
                                    text = stringResource(id = R.string.no_rides_available),
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        // Spacer between sections
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Taking Rides Section
                        item {
                            SectionTitle(title = stringResource(id = R.string.taking_ride))
                        }

                        if (ridesAsPassenger.isNotEmpty()) {
                            items(ridesAsPassenger) { ride ->
                                RideCard(
                                    ride = ride,
                                    role = stringResource(id = R.string.taking_ride),
                                    onDetailsClick = {
                                        navController.navigate("RideDetails/${ride.id}")
                                    },
                                    onRemoveOrLeaveClick = {
                                        rideViewModel.leaveRide(ride.id)
                                    }
                                )
                            }
                        } else {
                            item {
                                Text(
                                    text = stringResource(id = R.string.no_rides_available),
                                    color = Color.Gray,
                                    fontSize = 16.sp
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header com ícone e título
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Ride Icon",
                    tint = Color(0xFF3F51B5),
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = "Role: $role",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3F51B5),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informações da ride
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("From: ${ride.startingPoint}", fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text("To: ${ride.finalDestination}", fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text("Date: ${ride.startingDate}", color = Color.Gray)
                Text("Available Seats: ${ride.availablePlaces}", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Espaça os botões igualmente
            ) {
                Button(
                    onClick = onDetailsClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Details",
                        color = Color.White,
                        fontSize = 13.sp, // Reduz a fonte
                        maxLines = 1
                    )
                }

                if (role == "Giving Ride" && onManageRequestsClick != null) {
                    Button(
                        onClick = onManageRequestsClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "Requests",
                            color = Color.White,
                            fontSize = 13.sp, // Fonte menor
                            maxLines = 1
                        )
                    }
                }

                if (onRemoveOrLeaveClick != null) {
                    Button(
                        onClick = onRemoveOrLeaveClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (role == "Giving Ride") Color(0xFFFF9800) else Color(0xFFF44336)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = if (role == "Giving Ride") "Travelers" else "Leave",
                            color = Color.White,
                            fontSize = 13.sp, // Reduz o tamanho da fonte
                            maxLines = 1
                        )
                    }
                }
            }

        }
    }
}





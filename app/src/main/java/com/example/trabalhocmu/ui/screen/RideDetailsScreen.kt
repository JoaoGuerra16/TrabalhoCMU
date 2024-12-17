package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.room.entity.RideParticipant
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideDetailsScreen(navController: NavController, rideId: Int, rideViewModel: RideViewModel) {
    val participants by rideViewModel.getParticipants(rideId).collectAsState(initial = emptyList())
    val ride = rideViewModel.getRideById(rideId).collectAsState(initial = null)
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.8f
        )
        SidebarScaffold(navController = navController) { paddingValues ->
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.ride_details),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily,
                                color = Color.Black
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent,
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Detalhes da Ride
                        ride.value?.let { currentRide ->
                            RideDetailCard(currentRide)
                            Spacer(modifier = Modifier.height(16.dp))

                            if (currentRide.ownerEmail == currentUserEmail) {
                                when (currentRide.status) {
                                    stringResource(id = R.string.planned)  -> {
                                        Button(
                                            onClick = {
                                                rideViewModel.startRide(rideId)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text("Start Ride", color = Color.White, fontSize = 18.sp)
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = {
                                                rideViewModel.cancelRide(rideId)
                                                navController.popBackStack()
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text("Cancel Ride", color = Color.White, fontSize = 18.sp)
                                        }
                                    }
                                    stringResource(id = R.string.in_progress) -> {
                                        Button(
                                            onClick = {
                                                rideViewModel.completeRide(rideId)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text("Complete Ride", color = Color.White, fontSize = 18.sp)
                                        }
                                    }
                                    stringResource(id = R.string.completed)-> {
                                        Text(
                                            "Ride Completed",
                                            fontSize = 18.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Título da seção de participantes
                        Text(
                            stringResource(id = R.string.passengers) ,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Lista de participantes
                        LazyColumn {
                            items(participants) { participant ->
                                ParticipantCard(participant)
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
                            Text(stringResource(id = R.string.back), color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun RideDetailCard(ride: Ride) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF9F9F9), Color(0xFFEAEAEA))
                    )
                )
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = stringResource(id = R.string.ride_details),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Informações do Ride
            InfoRow(stringResource(id = R.string.from), ride.startingPoint)
            InfoRow(stringResource(id = R.string.to), ride.finalDestination)
            InfoRow(stringResource(id = R.string.starting_date), ride.startingDate)
            InfoRow(stringResource(id = R.string.end_date), ride.executedArrival)
            InfoRow(stringResource(id = R.string.available_seats), "${ride.availablePlaces}")
            InfoRow(stringResource(id = R.string.driver_email), ride.ownerEmail)

            Spacer(modifier = Modifier.height(8.dp))

            // Opções Pets, Baggage e Smoking
            RowField(stringResource(id = R.string.pets_allowed), ride.isPetsAllowed)
            RowField(stringResource(id = R.string.baggage_allowed), ride.isBaggageAllowed)
            RowField(stringResource(id = R.string.smoking_allowed), ride.isSmokingAllowed)
        }
    }
}

@Composable
fun RowField(label: String, isAllowed: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF777777)
        )
        Icon(
            imageVector = if (isAllowed) Icons.Filled.Check else Icons.Outlined.Close,
            contentDescription = null,
            tint = if (isAllowed) Color(0xFF4CAF50) else Color(0xFFF44336)
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF555555),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ParticipantCard(participant: RideParticipant) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Participant Email",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = participant.userEmail,
                    fontSize = 16.sp,
                    color = Color(0xFF454B60),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
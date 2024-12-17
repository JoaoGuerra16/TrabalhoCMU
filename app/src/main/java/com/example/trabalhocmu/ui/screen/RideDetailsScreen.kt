package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
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
                                text = "Ride Details",
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
                                    "PLANNED" -> {
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
                                    "IN_PROGRESS" -> {
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
                                    "COMPLETED" -> {
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
                            "Passengers",
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
                            Text("Back", color = Color.White, fontSize = 18.sp)
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
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFf2f2f2), Color(0xFFe6e6e6))
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "From: ${ride.startingPoint}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                fontSize = 18.sp
            )
            Text(
                text = "To: ${ride.finalDestination}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Date Inicio: ${ride.startingDate}", color = Color(0xFF777777), fontSize = 16.sp)
            Text("Date Termino: ${ride.executedArrival}", color = Color(0xFF777777), fontSize = 16.sp)
            Text(
                "Available Seats: ${ride.availablePlaces}",
                color = Color(0xFF777777),
                fontSize = 16.sp
            )
            Text("Driver Email: ${ride.ownerEmail}", color = Color(0xFF777777), fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Campos Pets, Baggage e Smoking
            RowField("Pets allowed", ride.isPetsAllowed)
            RowField("Baggage allowed", ride.isBaggageAllowed)
            RowField("Smoking allowed", ride.isSmokingAllowed)

        }
    }
}
@Composable
fun RowField(label: String, isAllowed: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color(0xFF777777),
            fontSize = 16.sp
        )
        Icon(
            imageVector = if (isAllowed) Icons.Default.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = if (isAllowed) Color(0xFF4CAF50) else Color.Gray // Verde se true, cinza se false
        )
    }
}
@Composable
fun ParticipantCard(participant: RideParticipant) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "User email: ${participant.userEmail}",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF454B60),
                fontSize = 16.sp
            )
        }
    }
}


package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily


// Composable da página StartRide
@Composable
fun StartRide(navController: NavController, from: String?, to: String?, startTime: String?, arrivalTime: String?, date: String?, availableSeats: String, passengers: List<Passenger>) {
    SidebarScaffold(navController = navController) { paddingValues ->

        var showDialog by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título e subtítulo
                Text(
                    text = "My rides",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Giving a ride",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Exibição das informações da viagem
                UserInfoStartRide(label = "Starting point", info = from ?: "N/A")
                UserInfoStartRide(label = "Final destination", info = to ?: "N/A")
                UserInfoStartRide(label = "Starting date", info = date ?: "N/A")
                UserInfoStartRide(label = "Expected arrival", info = arrivalTime ?: "N/A")
                UserInfoStartRide(label = "Available places", info = availableSeats)

                Spacer(modifier = Modifier.height(15.dp))

                // Checkboxes de permissões
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pets allowed", fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null) // Marcação fixa
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Baggage allowed", fontWeight = FontWeight.Bold)
                    Checkbox(checked = false, onCheckedChange = null) // Marcação fixa
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Smoking allowed", fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null) // Marcação fixa
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Exibindo os passageiros
                Text(
                    text = "Passengers:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                passengers.forEach { passenger ->
                    PassengerCard(
                        profileImage = passenger.profileImage,
                        name = passenger.name,
                        gender = passenger.gender,
                        pickup = passenger.pickup
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão para encerrar a viagem
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "End ride", color = Color.White, fontFamily = PoppinsFamily)
                    }
                }
            }

            // Diálogo de confirmação
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = "Confirm",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            fontFamily = PoppinsFamily
                        )
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to end this ride?",
                            fontFamily = PoppinsFamily
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                // Lógica para encerrar a ride
                            }
                        ) {
                            Text(text = "Yes", fontFamily = PoppinsFamily)
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(text = "No", fontFamily = PoppinsFamily)
                        }
                    }
                )
            }
        }
    }
}

// Composable para exibir informações do usuário
@Composable
fun UserInfoStartRide(label: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = info)
    }
}

// Composable para exibir cada passageiro
@Composable
fun PassengerCard(profileImage: Int, name: String, gender: String, pickup: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(CircleShape)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem de perfil
        Image(
            painter = painterResource(id = profileImage),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // Informações do passageiro
        Column {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Gender: $gender", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Pickup: $pickup", fontSize = 14.sp, color = Color.Gray)
        }
    }
}


// Modelo de dados para o Passageiro
data class Passenger(
    val profileImage: Int, // ID do recurso da imagem do perfil
    val name: String,
    val gender: String,
    val pickup: String
)

@Preview(showBackground = true)
@Composable
fun StartRidePreview() {
    val mockPassengers = listOf(
        Passenger(R.drawable.profile, "Maria", "Female", "Lisbon"),
        Passenger(R.drawable.profile, "Maria2", "Female", "Porto"),
        Passenger(R.drawable.profile, "Maria3", "Female", "Coimbra")
    )
    val navController = rememberNavController()

    StartRide(
        navController = navController,
        from = "Lisbon",
        to = "Porto",
        startTime = "08:00 AM",
        arrivalTime = "11:00 AM",
        date = "2024-11-17",
        availableSeats = "3",
        passengers = mockPassengers
    )
}

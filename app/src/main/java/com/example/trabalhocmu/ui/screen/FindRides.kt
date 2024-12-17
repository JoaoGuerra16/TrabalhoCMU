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
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.RideViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter




@Composable
fun FindRidesScreen(navController: NavController, rideViewModel: RideViewModel) {
    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }

    SidebarScaffold(navController = navController) { paddingValues ->
        val selectDateText = stringResource(id = R.string.select_date)
        val scrollState = rememberScrollState()
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        val selectedDate = remember { mutableStateOf(selectDateText) }
        val context = LocalContext.current
        val rides by rideViewModel.getAvailableRides().collectAsState(initial = emptyList())

        val showDatePicker = {
            val datePicker = DatePickerDialog(context, { _, year, month, dayOfMonth ->
                selectedDate.value = "$dayOfMonth/${month + 1}/$year"
            }, 2024, 0, 1)
            datePicker.show()
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Alinha os elementos horizontalmente no centro
        ) {
            Text(
                text = "Available Rides",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily,
                modifier = Modifier.fillMaxWidth(), // Faz o texto ocupar toda a largura disponível
                textAlign = TextAlign.Center // Centraliza o texto horizontalmente
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre o título e a lista

            LazyColumn {
                items(rides) { ride ->
                    RideCard(
                        ride = ride,
                        onDetailsClick = {
                            navController.navigate("RideDetails/${ride.id}")
                        },
                        onRequestClick = {
                            navController.navigate("RideRequestScreen/${ride.id}")
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun RideCard(ride: Ride, onDetailsClick: () -> Unit, onRequestClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "Ride Icon",
                        tint = Color(0xFF3F51B5),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Available Ride",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3F51B5)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informações da Ride
            Text("From: ${ride.startingPoint}", fontWeight = FontWeight.SemiBold, color = Color.Black)
            Text("To: ${ride.finalDestination}", fontWeight = FontWeight.SemiBold, color = Color.Black)
            Text("Date: ${ride.startingDate}", color = Color.Gray)
            Text("Available Seats: ${ride.availablePlaces}", color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // Botões de Ação
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDetailsClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Details Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Details", color = Color.White)
                }

                Button(
                    onClick = onRequestClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Request Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Request", color = Color.White)
                }
            }
        }
    }
}
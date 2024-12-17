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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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


        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Available Rides", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)

            LazyColumn {
                items(rides) { ride ->
                    RideCard(
                        ride = ride,
                        onDetailsClick = {
                            navController.navigate("RideDetails/${ride.id}")
                        },
                        onRequestClick = { // Nova lógica aqui
                            rideViewModel.requestToJoinRide(ride.id)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("From: ${ride.startingPoint}")
            Text("To: ${ride.finalDestination}")
            Text("Date: ${ride.startingDate}")
            Text("Available Seats: ${ride.availablePlaces}")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onDetailsClick) {
                    Text("Details")
                }
                Button(onClick = onRequestClick) { // Mudança aqui
                    Text("Request Ride")
                }
            }
        }
    }
}

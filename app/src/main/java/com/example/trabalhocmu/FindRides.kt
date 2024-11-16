package com.example.trabalhocmu

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindRides(navController: NavController) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val scrollState = rememberScrollState()
        val currentLanguage = remember { mutableStateOf("PT") }
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        val selectedDate = remember { mutableStateOf("Select Date") }
        val context = LocalContext.current

        // Função para exibir o DatePickerDialog
        val showDatePicker = {
            val datePicker = DatePickerDialog(context, { _, year, month, dayOfMonth ->
                selectedDate.value = "$dayOfMonth/${month + 1}/$year"
            }, 2024, 0, 1)  // Data inicial (ano, mês, dia)
            datePicker.show()
        }

        // Rides disponíveis
        val rides = listOf(
            Ride("Lisboa", "Porto", 3, "08:00", "12:00", LocalDate.of(2024, 11, 13)),
            Ride("Porto", "Coimbra", 2, "13:00", "15:00", LocalDate.of(2024, 11, 14)),
            Ride("Lisboa", "Faro", 4, "09:00", "13:00", LocalDate.of(2024, 11, 15)),
            Ride("Coimbra", "Lisboa", 1, "11:00", "14:00", LocalDate.of(2024, 11, 15)),
            Ride("Faro", "Lisboa", 3, "14:00", "18:00", LocalDate.of(2024, 11, 15)),
            Ride("Lisboa", "Braga", 2, "06:00", "10:00", LocalDate.of(2024, 11, 16)),
            Ride("Porto", "Faro", 4, "17:00", "22:00", LocalDate.of(2024, 11, 16)),
            Ride("Lisboa", "Coimbra", 2, "10:00", "12:30", LocalDate.of(2024, 11, 17))
        )

        // Guardar as rides filtradas
        var filteredRides by remember { mutableStateOf(rides) }

        // Filtrar rides pela data selecionada
        LaunchedEffect(selectedDate.value) {
            filteredRides = if (selectedDate.value != "Select Date") {
                val selectedDateParsed = LocalDate.parse(selectedDate.value, DateTimeFormatter.ofPattern("d/M/yyyy"))
                rides.filter { it.date == selectedDateParsed }
            } else {
                rides
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(text = "Find Rides", fontSize = 25.sp, fontWeight = FontWeight.Bold)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(40.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { showDatePicker() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Select Date")
                        }

                        // Botão para cancelar a seleção de data
                        if (selectedDate.value != "Select Date") {
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = { selectedDate.value = "Select Date" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Cancel Date")
                            }
                        }
                    }

                    if (selectedDate.value != "Select Date") {
                        Text(
                            text = "Selected Date: ${selectedDate.value}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Exibir mensagem se não houver rides disponíveis
                    if (filteredRides.isEmpty()) {
                        Text(
                            text = "No rides available",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    } else {
                        // Exibir as rides filtradas
                        filteredRides.forEach { ride ->
                            RideInformation(
                                navController = navController,
                                from = ride.from,
                                to = ride.to,
                                availableSeats = ride.availableSeats,
                                startTime = ride.startTime,
                                arrivalTime = ride.arrivalTime,
                                date = ride.date
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = if (currentLanguage.value == "ENG") "ENG | PT" else "PT | ENG",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RideInformation(  navController: NavController,from: String, to: String, availableSeats: Int, startTime: String, arrivalTime: String , date: LocalDate) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("ride_Details/$from/$to/${date.toString()}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ride Information", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "From:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = from, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "To:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = to, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Available Seats:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = availableSeats.toString(), fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Start Time:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = startTime, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Arrival Time:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = arrivalTime, fontSize = 16.sp)
            }// Exibe a data da ride
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Date:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = date.toString(), fontSize = 16.sp)
            }
        }
    }
}

data class Ride(
    val from: String,
    val to: String,
    val availableSeats: Int,
    val startTime: String,
    val arrivalTime: String,
    val date: LocalDate
)

@Preview
@Composable
fun FindRidesPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    FindRides(navController)
}

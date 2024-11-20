package com.example.trabalhocmu.ui.screen

import android.app.DatePickerDialog
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.component.SidebarScaffold
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import kotlin.random.Random


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RidesHistory(navController: NavController) {
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
            RideTeste(from = "New York", to = "Los Angeles", availableSeats = 3, startTime = "10:00 AM", arrivalTime = "6:00 PM", date = LocalDate.now(), isGivingRide = true),
            RideTeste(from = "Chicago", to = "San Francisco", availableSeats = 2, startTime = "9:00 AM", arrivalTime = "5:00 PM", date = LocalDate.now(), isGivingRide = true),
            RideTeste(from = "Boston", to = "Miami", availableSeats = 0, startTime = "8:00 AM", arrivalTime = "4:00 PM", date = LocalDate.now(), isGivingRide = false),
            RideTeste(from = "Dallas", to = "Austin", availableSeats = 0, startTime = "11:00 AM", arrivalTime = "7:00 PM", date = LocalDate.now(), isGivingRide = false)
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
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top // Ajuste para que o conteúdo fique alinhado ao topo
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ride's History",
                        fontFamily = PoppinsFamily,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),  // Esse modificador faz com que o texto ocupe o espaço disponível
                        textAlign = TextAlign.Center // Centraliza o texto dentro do espaço
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search",  fontFamily = PoppinsFamily) },

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
                        Text(text = "Select Date",
                            fontFamily = PoppinsFamily,)
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
                            Text(text = "Cancel Date",
                                fontFamily = PoppinsFamily,)
                        }
                    }
                }

                if (selectedDate.value != "Select Date") {
                    Text(
                        text = "Selected Date: ${selectedDate.value},",
                        fontFamily = PoppinsFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Mostra mensagem se não houver rides disponíveis
                if (filteredRides.isEmpty()) {
                    Text(
                        text = "No rides available",
                        fontFamily = PoppinsFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                } else {
                    // Mostra as rides filtradas
                    filteredRides.forEach { ride ->
                        val isGivingRide = Random.nextBoolean()
                        RidesHistoryInformation(
                            navController = navController,
                            from = ride.from,
                            to = ride.to,
                            availableSeats = ride.availableSeats,
                            startTime = ride.startTime,
                            arrivalTime = ride.arrivalTime,
                            date = ride.date,
                            isGivingRide = isGivingRide)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RidesHistoryInformation(
    from: String,
    to: String,
    availableSeats: Int,
    startTime: String,
    arrivalTime: String,
    date: LocalDate,
    isGivingRide: Boolean,
    navController: NavController
) {
    // Variável para controlar se o dialog de confirmação deve aparecer
    var showDialog by remember { mutableStateOf(false) }

    // Caixa de diálogo de confirmação

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isGivingRide) "Giving a Ride" else "Taking a Ride",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Route:", fontSize = 16.sp, fontWeight = FontWeight.Bold,  fontFamily = PoppinsFamily)
                Text(text = "$from to $to", fontSize = 16.sp,  fontFamily = PoppinsFamily,)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Start:", fontSize = 16.sp, fontWeight = FontWeight.Bold , fontFamily = PoppinsFamily)
                Text(text = "$startTime", fontSize = 16.sp,  fontFamily = PoppinsFamily)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Arrival:", fontSize = 16.sp, fontWeight = FontWeight.Bold,  fontFamily = PoppinsFamily)
                Text(text = "$arrivalTime", fontSize = 16.sp,  fontFamily = PoppinsFamily)
            }

            // Mostra "Available Seats" apenas se for "giving a ride"
            if (isGivingRide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Available Seats:", fontSize = 16.sp, fontWeight = FontWeight.Bold , fontFamily = PoppinsFamily)
                    Text(text = availableSeats.toString(), fontSize = 16.sp,  fontFamily = PoppinsFamily)
                }
            }

            // Mostra a data da viagem
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Date:", fontSize = 16.sp, fontWeight = FontWeight.Bold,   fontFamily = PoppinsFamily)
                Text(text = date.toString(), fontSize = 16.sp,   fontFamily = PoppinsFamily)
            }

        }
    }
}

// Data class para representar uma viagem
data class RidesHistory(
    val from: String,
    val to: String,
    val availableSeats: Int,
    val startTime: String,
    val arrivalTime: String,
    val date: LocalDate,
    val isGivingRide: Boolean
)
@Preview
@Composable
fun RidesHistoryPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    RidesHistory(navController)
}

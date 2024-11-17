package com.example.trabalhocmu

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyRides(navController: NavController) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val selectDateText = stringResource(id = R.string.select_date)
        val scrollState = rememberScrollState()
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        val selectedDate = remember { mutableStateOf(selectDateText) }
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
            filteredRides = if (selectedDate.value != selectDateText) {
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
                    Box(
                        modifier = Modifier.size(48.dp) // Define um tamanho fixo, simulando o espaço de um ícone
                    )
                    Text(
                        text = stringResource(id = R.string.my_rides),
                        fontFamily = PoppinsFamily,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),  // Esse modificador faz com que o texto ocupe o espaço disponível
                        textAlign = TextAlign.Center // Centraliza o texto dentro do espaço
                    )
                    // Botão "+"
                    IconButton(
                        onClick = { /* Ação do botão "+" */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_ride),
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(stringResource(id = R.string.search), fontFamily = PoppinsFamily) },
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
                        Text(text = stringResource(id = R.string.select_date),
                            fontFamily = PoppinsFamily,)
                    }

                    // Botão para cancelar a seleção de data
                    if (selectedDate.value != stringResource(id = R.string.select_date)) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { selectedDate.value = selectDateText },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = stringResource(id = R.string.cancel_date),
                                fontFamily = PoppinsFamily,)
                        }
                    }
                }

                if (selectedDate.value != stringResource(id = R.string.select_date)) {
                    Text(
                        text = "${stringResource(id = R.string.selected_date)}: ${selectedDate.value}",
                        fontFamily = PoppinsFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Mostra mensagem se não houver rides disponíveis
                if (filteredRides.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.no_rides_available),
                        fontFamily = PoppinsFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                } else {
                    // Mostra as rides filtradas
                    filteredRides.forEach { ride ->
                        MyRidesInformation(
                            navController = navController,
                            from = ride.from,
                            to = ride.to,
                            availableSeats = ride.availableSeats,
                            startTime = ride.startTime,
                            arrivalTime = ride.arrivalTime,
                            date = ride.date,
                            isGivingRide = ride.availableSeats > 0,
                            onCancelRide = { /* Não esquecer de fazer a lógica para cancelar a ride */ }

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MyRidesInformation(
    from: String,
    to: String,
    availableSeats: Int,
    startTime: String,
    arrivalTime: String,
    date: LocalDate,
    isGivingRide: Boolean,
    onCancelRide: () -> Unit,
    navController: NavController
) {
    // Variável para controlar se o dialog de confirmação deve aparecer
    var showDialog by remember { mutableStateOf(false) }

    // Caixa de diálogo de confirmação
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.confirm_cancellation)) },
            text = { Text(stringResource(id = R.string.confirm_cancel_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        // Lógica para cancelar a ride (por exemplo, remover a ride da lista ou atualizar seu estado)
                        onCancelRide()
                        showDialog = false // Fecha o diálogo
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = stringResource(id = R.string.yes), color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }, // Apenas fecha o diálogo
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text(text = stringResource(id = R.string.no), color = Color.White)
                }
            }
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (isGivingRide) {
                    navController.navigate("MyRidesGivingARide/$from/$to/$startTime/$arrivalTime/${date.toString()}/${availableSeats.toString()}")
                } else {
                navController.navigate("MyRidesTakingARide/$from/$to/$startTime/$arrivalTime/${date.toString()}/${availableSeats.toString()}")
            }
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isGivingRide) stringResource(id = R.string.giving_ride) else stringResource(id = R.string.taking_ride),
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
                Text(text = stringResource(id = R.string.route), fontSize = 16.sp, fontWeight = FontWeight.Bold,  fontFamily = PoppinsFamily)
                Text(text = "$from to $to", fontSize = 16.sp,  fontFamily = PoppinsFamily,)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.start), fontSize = 16.sp, fontWeight = FontWeight.Bold , fontFamily = PoppinsFamily)
                Text(text = "$startTime", fontSize = 16.sp,  fontFamily = PoppinsFamily)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.arrival), fontSize = 16.sp, fontWeight = FontWeight.Bold,  fontFamily = PoppinsFamily)
                Text(text = "$arrivalTime", fontSize = 16.sp,  fontFamily = PoppinsFamily)
            }

            // Mostra "Available Seats" apenas se for "giving a ride"
            if (isGivingRide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.available_seats), fontSize = 16.sp, fontWeight = FontWeight.Bold , fontFamily = PoppinsFamily)
                    Text(text = availableSeats.toString(), fontSize = 16.sp,  fontFamily = PoppinsFamily)
                }
            }

            // Mostra a data da viagem
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.date), fontSize = 16.sp, fontWeight = FontWeight.Bold,   fontFamily = PoppinsFamily)
                Text(text = date.toString(), fontSize = 16.sp,   fontFamily = PoppinsFamily)
            }

            // Botão para cancelar a ride
            Spacer(modifier = Modifier.height(16.dp)) // Espaço antes do botão
            Button(
                onClick = { showDialog = true }, // Exibe o diálogo de confirmação
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp), // Bordas arredondadas no botão
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            ) {
                Text(
                    text = stringResource(id = R.string.cancel_ride),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
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

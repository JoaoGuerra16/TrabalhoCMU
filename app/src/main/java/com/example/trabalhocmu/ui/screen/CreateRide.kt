package com.example.trabalhocmu.ui.screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.API.util.MapViewComposable
import com.example.trabalhocmu.R
import com.example.trabalhocmu.notificacoes.createNotification
import com.example.trabalhocmu.notificacoes.sendNotification
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun CreateRide(navController: NavController) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val scrollState = rememberScrollState()
        val rideViewModel = RideViewModel(LocalContext.current)
        val context = LocalContext.current

        var startingPoint by remember { mutableStateOf("") }
        var finalDestination by remember { mutableStateOf("") }
        var startingDate by remember { mutableStateOf("") }
        var executedArrival by remember { mutableStateOf("") }
        var availablePlaces by remember { mutableStateOf("") }

        val ownerEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""// Email do condutor
        var isPetsAllowed by remember { mutableStateOf(false) }
        var isBaggageAllowed by remember { mutableStateOf(false) }
        var isSmokingAllowed by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            createNotification(context)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.create_ride_title),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = stringResource(id = R.string.basic_trip_information),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(15.dp))


                CustomOutlinedTextField(
                    value = startingPoint,
                    onValueChange = { startingPoint = it },
                    label = stringResource(id = R.string.starting_point)
                )

                Spacer(modifier = Modifier.height(10.dp))


                CustomOutlinedTextField(
                    value = finalDestination,
                    onValueChange = { finalDestination = it },
                    label = stringResource(id = R.string.final_destination)
                )

                Spacer(modifier = Modifier.height(20.dp))
                MapViewComposable(startingPoint = startingPoint, finalDestination = finalDestination)

                Spacer(modifier = Modifier.height(20.dp))


                CustomOutlinedTextField(
                    value = startingDate,
                    onValueChange = { startingDate = it },
                    label = "Starting Date"
                )

                Spacer(modifier = Modifier.height(10.dp))


                CustomOutlinedTextField(
                    value = executedArrival,
                    onValueChange = { executedArrival = it },
                    label = "Executed Arrival"
                )

                Spacer(modifier = Modifier.height(10.dp))


                CustomOutlinedTextField(
                    value = availablePlaces,
                    onValueChange = { availablePlaces = it },
                    label = "Available Places"
                )

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.pets_allowed), fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                    Checkbox(
                        checked = isPetsAllowed,
                        onCheckedChange = { isPetsAllowed = it }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.baggage_allowed), fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                    Checkbox(
                        checked = isBaggageAllowed,
                        onCheckedChange = { isBaggageAllowed = it }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.smoking_allowed), fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                    Checkbox(
                        checked = isSmokingAllowed,
                        onCheckedChange = { isSmokingAllowed = it }
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))


                Button(
                    onClick = {
                        val availablePlacesInt = availablePlaces.toIntOrNull()
                        if (availablePlacesInt != null && ownerEmail.isNotBlank()) {
                            rideViewModel.createRide(
                                startingPoint = startingPoint,
                                finalDestination = finalDestination,
                                startingDate = startingDate,
                                executedArrival = executedArrival,
                                availablePlaces = availablePlacesInt,
                                isPetsAllowed = isPetsAllowed,
                                isBaggageAllowed = isBaggageAllowed,
                                isSmokingAllowed = isSmokingAllowed,
                                ownerEmail = ownerEmail,
                            )

                            // Mostrar Toast de sucesso
                            Toast.makeText(
                                context, // Usar o contexto obtido
                                context.getString(R.string.creation_successful),
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navegar para a tela "My Rides"
                            navController.navigate("My Rides") {
                                popUpTo("create_ride") { inclusive = true }
                                sendNotification(context, "Create", "Ride criada com sucesso")
                            }
                        } else {
                            // Mostrar um erro ao utilizador
                            Toast.makeText(
                                context, // Usar o contexto obtido
                                context.getString(R.string.fill_fields_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
                ) {
                    Text(
                        text = stringResource(id = R.string.next),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFEFEFEF) // Definindo o fundo como branco
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateRide() {
    // Criando um NavController para o Preview
    val navController = rememberNavController()

    // Chama o composable passando parâmetros fictícios
    CreateRide(
        navController = navController,
    )
}
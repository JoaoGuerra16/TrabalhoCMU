package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.API.models.MapViewComposable
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.trabalhocmu.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideRequestScreen(
    rideId: Int,
    rideViewModel: RideViewModel,
    navController: NavController
) {
    val ride by rideViewModel.getRideById(rideId).collectAsState(initial = null)

    var isNormalRoute by remember { mutableStateOf(true) }
    var pickupLocation by remember { mutableStateOf("") }
    var dropoffLocation by remember { mutableStateOf("") }






        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                                text = stringResource(id = R.string.request),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent,
                content = { padding ->
                    ride?.let { rideDetails ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            // Título
                            Text(
                                stringResource(id = R.string.ride_details),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                Text(
                                    text = stringResource(id = R.string.from),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = rideDetails.startingPoint,
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }

                            Row {
                                Text(
                                    text = stringResource(id = R.string.to),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = rideDetails.finalDestination,
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            Text(
                                "Available Places: ${rideDetails.availablePlaces}",
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(stringResource(id = R.string.choose_your_request_option), fontWeight = FontWeight.Bold, fontSize = 18.sp)

                            // Botões de Opção
                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                RadioButton(selected = isNormalRoute, onClick = { isNormalRoute = true })
                                Text(stringResource(id = R.string.route_normal_route), fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(16.dp))
                                RadioButton(selected = !isNormalRoute, onClick = { isNormalRoute = false })
                                Text("Custom Pickup/Dropoff", fontSize = 16.sp)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Mapa
                            if (!isNormalRoute) {
                                Text(stringResource(id = R.string.select_pickup), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                MapViewComposable(
                                    startingPoint = rideDetails.startingPoint,
                                    finalDestination = rideDetails.finalDestination,
                                    enableSelection = true,
                                    onPickupSelected = { pickupLocation = it },
                                    onDropoffSelected = { dropoffLocation = it }
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                if (pickupLocation.isNotEmpty()) {
                                    Text("Selected Pickup: $pickupLocation", color = Color(0xFF757575))
                                }

                                if (dropoffLocation.isNotEmpty()) {
                                    Text("Selected Dropoff: $dropoffLocation", color = Color(0xFF757575))
                                }
                            } else {
                                Text(stringResource(id = R.string.route_normal_route), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                MapViewComposable(
                                    startingPoint = rideDetails.startingPoint,
                                    finalDestination = rideDetails.finalDestination,
                                    enableSelection = false
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botão de Enviar Pedido
                            Button(
                                onClick = {
                                    rideViewModel.requestToJoinRide(
                                        rideId = rideId,
                                        isNormalRoute = isNormalRoute,
                                        pickupLocation = if (!isNormalRoute) pickupLocation else null,
                                        dropoffLocation = if (!isNormalRoute) dropoffLocation else null
                                    )
                                    navController.popBackStack()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                            ) {
                                Text(stringResource(id = R.string.send_request), color = Color.White, fontSize = 18.sp)
                            }
                        }
                    } ?: run {
                        // Indicador de carregamento
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF4CAF50))
                        }
                    }
                }
            )
        }
    }
}

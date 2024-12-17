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
import com.example.trabalhocmu.viewmodel.RideViewModel


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

    ride?.let { rideDetails ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título da tela
            Text("Ride Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Informações da viagem
            Text("From: ${rideDetails.startingPoint}")
            Text("To: ${rideDetails.finalDestination}")
            Text("Available Places: ${rideDetails.availablePlaces}")

            Spacer(modifier = Modifier.height(16.dp))
            Text("Choose your request option:")

            // Botões de escolha: Normal Route ou Custom Pickup/Dropoff
            Row(modifier = Modifier.padding(top = 8.dp)) {
                RadioButton(selected = isNormalRoute, onClick = { isNormalRoute = true })
                Text("Normal Route")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = !isNormalRoute, onClick = { isNormalRoute = false })
                Text("Custom Pickup/Dropoff")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mapa com lógica específica para Custom Pickup/Dropoff
            if (!isNormalRoute) {
                // Mostrar campos apenas se Custom Pickup/Dropoff estiver ativo
                Text("Select Pickup and Dropoff on the map:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                // MapViewComposable com funcionalidade de cliques para selecionar locais
                MapViewComposable(
                    startingPoint = rideDetails.startingPoint,
                    finalDestination = rideDetails.finalDestination,
                    enableSelection = true,
                    onPickupSelected = { pickupLocation = it },
                    onDropoffSelected = { dropoffLocation = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mostrar os locais selecionados no mapa
                if (pickupLocation.isNotEmpty()) {
                    Text("Selected Pickup: $pickupLocation")
                }
                if (dropoffLocation.isNotEmpty()) {
                    Text("Selected Dropoff: $dropoffLocation")
                }
            } else {
                // Caso seja Normal Route, apenas exibir o mapa com a rota
                Text("Normal Route:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                MapViewComposable(
                    startingPoint = rideDetails.startingPoint,
                    finalDestination = rideDetails.finalDestination,
                    enableSelection = false
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para enviar o pedido
            Button(
                onClick = {
                    rideViewModel.requestToJoinRide(
                        rideId = rideId,
                        isNormalRoute = isNormalRoute,
                        pickupLocation = if (!isNormalRoute) pickupLocation else null,
                        dropoffLocation = if (!isNormalRoute) dropoffLocation else null
                    )
                    navController.popBackStack() // Voltar para a tela anterior
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Request")
            }
        }
    } ?: run {
        // Mostra um indicador de carregamento enquanto os dados não chegam
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}

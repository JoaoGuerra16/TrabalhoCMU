package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily

@Composable
fun MyRidesGivingARide(  navController: NavController, from: String?, to: String?, startTime: String?, arrivalTime: String?, date: String?, availableSeats: String){
    SidebarScaffold(navController = navController) { paddingValues ->
        val currentLanguage = remember { mutableStateOf("PT") }
        val scrollState = rememberScrollState()
        var buttonText by remember { mutableStateOf("Ask for ride") }
        var isButtonEnabled by remember { mutableStateOf(true) }
        // Estado para controlar se o mapa e mensagens serão exibidos
        var isRouteChecked by remember { mutableStateOf(false) }
        val darkBlue = Color(0xFF1E3A8A)

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.my_rides),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily

                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.giving_ride),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFamily

                )
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoRowGivingARide(label = stringResource(id = R.string.starting_point), info = from ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRowGivingARide(label = stringResource(id = R.string.final_destination), info = to ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRowGivingARide(label = stringResource(id = R.string.starting_date), info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRowGivingARide(label = stringResource(id = R.string.expected_arrival), info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRowGivingARide(label = stringResource(id = R.string.available_seats), info = availableSeats ?: "N/A")

                Spacer(modifier = Modifier.height(15.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.pets_allowed), fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null)  // Marcação fixa
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.baggage_allowed), fontWeight = FontWeight.Bold)
                    Checkbox(checked = false, onCheckedChange = null)  // Marcação fixa
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.smoking_allowed), fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.navigate("RequestForRide") },
                        colors = ButtonDefaults.buttonColors(containerColor = darkBlue) // Cor azul
                    ) {
                        Text(
                            text = stringResource(id = R.string.people_request),
                            color = Color.White,
                            fontFamily = PoppinsFamily
                        )
                    }
                    // Botão "Start" verde
                    Button(
                        onClick = { (navController.navigate(  "StartRide/$from/$to/$startTime/$arrivalTime/$date/$availableSeats")) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text(text = stringResource(id = R.string.start), color = Color.White, fontFamily = PoppinsFamily)
                    }



                }
            }
        }
    }
}

@Composable
fun UserInfoRowGivingARide(label: String, info: String) {
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

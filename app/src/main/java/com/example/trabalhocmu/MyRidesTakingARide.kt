package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.theme.PoppinsFamily

@Composable
fun MyRidesTakingARide(  navController: NavController, from: String?, to: String?, startTime: String?, arrivalTime: String?, date: String?, availableSeats: String){
    SidebarScaffold(navController = navController) { paddingValues ->
        val currentLanguage = remember { mutableStateOf("PT") }
        val scrollState = rememberScrollState()
        var isButtonEnabled by remember { mutableStateOf(true) }
        // Estado para controlar se o mapa e mensagens serão exibidos
        var isRouteChecked by remember { mutableStateOf(false) }
        var buttonState by remember { mutableStateOf("Waiting for confirmation") }
        var buttonColor by remember { mutableStateOf(Color(0xFF454B60)) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "My Rides",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))
                RatingStars(rating = 4)
                Spacer(modifier = Modifier.height(40.dp))

                UserInfoRow(label = "Starting point", info = from ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Finish point", info = to ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Starting date", info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Expected arrival", info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Available places", info = "2")

                Spacer(modifier = Modifier.height(15.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pets allowed", fontWeight = FontWeight.Bold)
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
                    Text(text = "Baggage allowed", fontWeight = FontWeight.Bold)
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
                    Text(text = "Smoking allowed", fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null)  // Marcação fixa
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) { val handleButtonClick = {
                    when (buttonState) {
                        "Waiting for confirmation" -> {
                            buttonState = "Confirmed"
                            buttonColor = Color(0xFF4CAF50) // Cor verde
                        }
                        "Confirmed" -> {
                            buttonState = "Recused"
                            buttonColor = Color(0xFFF44336) // Cor vermelha
                        }
                        "Recused" -> {
                            buttonState = "Waiting for confirmation"
                            buttonColor = Color(0xFF454B60) // Cor original
                        }
                    }
                }
                    Button(
                        onClick = { handleButtonClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor), // Cor do botão
                                modifier = Modifier
                                .widthIn(min = 250.dp) // Definindo largura mínima para o botão
                            .height(50.dp) // Definindo altura fixa
                    ) {
                        Text(
                            text = buttonState,
                            color = Color.White,
                            fontFamily = PoppinsFamily
                        )
                    }
            }
        }
    }
}
// Composable para mostrar as informações do usuário com label e valor
@Composable
fun UserInfoRowTakingARide(label: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = info)
    }

}}
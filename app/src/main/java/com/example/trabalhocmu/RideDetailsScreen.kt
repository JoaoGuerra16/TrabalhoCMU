package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@Composable
fun RideDetailsScreen(navController: NavController, from: String?, to: String?, date: String?) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val currentLanguage = remember { mutableStateOf("PT") }
        val scrollState = rememberScrollState()
        var buttonText by remember { mutableStateOf("Ask for ride") }
        var isButtonEnabled by remember { mutableStateOf(true) }
        // Estado para controlar se o mapa e mensagens serão exibidos
        var isRouteChecked by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Ride Details",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Need for a pick up?",
                        fontWeight = FontWeight.Bold)
                    Checkbox(
                        checked = isRouteChecked,
                        onCheckedChange = { isRouteChecked = it }
                    )
                }

                // Exibe o mapa e as mensagens somente se a checkbox estiver marcada
                if (isRouteChecked) {
                    Text(text = "Where do you want to be picked up?",
                        fontWeight = FontWeight.Bold

                    )
                    Image(
                        painter = painterResource(id = R.drawable.mapa),
                        contentDescription = "Mapa",
                        modifier = Modifier
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally)  // Garante que o mapa está centralizado
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pick up - Felgueiras, rua do jardim",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(30.dp))  // Adiciona um pequeno espaço entre as frases
                        Text(
                            text = "Drop off - Amarante, rua do Juíz",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de "Ask for ride"
                Button(
                    onClick = {
                        // Altera o texto e desativa o botão após o clique
                        buttonText = "Order sent"
                        isButtonEnabled = false
                    },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF454B60)
                    ),
                    enabled = isButtonEnabled // Desativa o botão se for false
                ) {
                    Text(text = buttonText)
                }
            }
        }
    }
}
// Composable para mostrar as informações do usuário com label e valor
@Composable
fun UserInfoRow(label: String, info: String) {
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



@Preview(showBackground = true)
@Composable
fun PreviewRideDetailsScreen() {
    val fakeNavController = rememberNavController()
    RideDetailsScreen(
        navController = fakeNavController,
        from = "Rua Dr. Ferreira, Felgueiras",
        to = "Rua Da Amadora, Lisboa",
        date = "2024-11-15"
    )
}

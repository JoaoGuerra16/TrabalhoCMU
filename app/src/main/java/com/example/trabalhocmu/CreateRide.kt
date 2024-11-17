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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
fun CreateRide(navController: NavController) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val currentLanguage = remember { mutableStateOf("PT") }
        val scrollState = rememberScrollState()
        var isButtonEnabled by remember { mutableStateOf(true) }
        // Estado para controlar se o mapa e mensagens serão exibidos
        var isRouteChecked by remember { mutableStateOf(false) }
        var buttonState by remember { mutableStateOf("Waiting for confirmation") }
        var buttonColor by remember { mutableStateOf(Color(0xFF454B60)) }

        // Estados para os campos editáveis
        var startingPoint by remember { mutableStateOf("") }
        var finalDestination by remember { mutableStateOf("") }
        var startingDate by remember { mutableStateOf("") }
        var executedArrival by remember { mutableStateOf("") }
        var availablePlaces by remember { mutableStateOf("") }

        // Estados para os checkboxes
        var isPetsAllowed by remember { mutableStateOf(false) }
        var isBaggageAllowed by remember { mutableStateOf(false) }
        var isSmokingAllowed by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Título principal
                Text(
                    text = "Create Ride",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Texto "Basic trip information"
                Text(
                    text = "Basic trip information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Caixa de texto para o "Starting Point"
                OutlinedTextField(
                    value = startingPoint,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { startingPoint = it },
                    label = { Text("Starting Point") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Caixa de texto para o "Final Destination"
                OutlinedTextField(
                    value = finalDestination,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { finalDestination = it },
                    label = { Text("Final Destination") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Mapa
                Image(
                    painter = painterResource(id = R.drawable.mapa),
                    contentDescription = "Mapa",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.CenterHorizontally)  // Garante que o mapa está centralizado
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Caixa de texto para o "Starting Date"
                OutlinedTextField(
                    value = startingDate,
                    onValueChange = { startingDate = it },
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("Starting Date") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Caixa de texto para o "Executed Arrival"
                OutlinedTextField(
                    value = executedArrival,
                    onValueChange = { executedArrival = it },
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("Executed Arrival") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Caixa de texto para o "Available Places"
                OutlinedTextField(
                    value = availablePlaces,
                    onValueChange = { availablePlaces = it },
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("Available Places") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Opções de checkboxes (Texto à esquerda e checkbox à direita)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pets Allowed", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
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
                    Text("Baggage Allowed", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
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
                    Text("Smoking Allowed", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                    Checkbox(
                        checked = isSmokingAllowed,
                        onCheckedChange = { isSmokingAllowed = it }
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botão NEXT
                Button(
                    onClick = {
                        // Navegar para a próxima tela
                        // Você pode colocar a navegação aqui
                        // Exemplo: navController.navigate("next_screen")
                    },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
                ) {
                    Text(
                        text = "NEXT",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
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

package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold


@Composable
fun CreateRide(navController: NavController) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val scrollState = rememberScrollState()

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

                // Usando o CustomOutlinedTextField para o "Starting Point"
                CustomOutlinedTextField(
                    value = startingPoint,
                    onValueChange = { startingPoint = it },
                    label = "Starting Point"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Usando o CustomOutlinedTextField para o "Final Destination"
                CustomOutlinedTextField(
                    value = finalDestination,
                    onValueChange = { finalDestination = it },
                    label = "Final Destination"
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

                // Usando o CustomOutlinedTextField para o "Starting Date"
                CustomOutlinedTextField(
                    value = startingDate,
                    onValueChange = { startingDate = it },
                    label = "Starting Date"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Usando o CustomOutlinedTextField para o "Executed Arrival"
                CustomOutlinedTextField(
                    value = executedArrival,
                    onValueChange = { executedArrival = it },
                    label = "Executed Arrival"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Usando o CustomOutlinedTextField para o "Available Places"
                CustomOutlinedTextField(
                    value = availablePlaces,
                    onValueChange = { availablePlaces = it },
                    label = "Available Places"
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

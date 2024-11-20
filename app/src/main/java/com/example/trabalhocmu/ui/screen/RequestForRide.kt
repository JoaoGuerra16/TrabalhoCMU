package com.example.trabalhocmu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.ui.theme.PoppinsFamily


@Composable
fun RequestForRide(navController: NavController) {
    // Scaffold do menu lateral
    SidebarScaffold(navController = navController) { paddingValues ->
        val scrollState = rememberScrollState()
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "People request",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Request Information exemplo
                RequestInformation(
                    navController = navController,
                    name = "Maria João",
                    gender = "Female",
                    pickupPoint = "Rua do jardim, Felgueiras",
                    onAccept = { /* Ação para aceitar a solicitação */ },
                    onDecline = { /* Ação para recusar a solicitação */ }
                )

                RequestInformation(
                    navController = navController,
                    name = "Maria Miguel",
                    gender = "Female",
                    pickupPoint = "Rua xpto",
                    onAccept = { /* Ação para aceitar a solicitação */ },
                    onDecline = { /* Ação para recusar a solicitação */ }

                )

                RequestInformation(
                    navController = navController,
                    name = "Maria Ângela",
                    gender = "Female",
                    pickupPoint = "Porto, rua do Porto",
                    onAccept = { /* Ação para aceitar a solicitação */ },
                    onDecline = { /* Ação para recusar a solicitação */ }
                )

                RequestInformation(
                    navController = navController,
                    name = "Maria Rafaela",
                    gender = "Female",
                    pickupPoint = "Lixa, rua do cemitério",
                    onAccept = { /* Ação para aceitar a solicitação */ },
                    onDecline = { /* Ação para recusar a solicitação */ }
                )
            }
        }
    }
}

@Composable
fun RequestInformation(
    name: String,
    gender: String,
    pickupPoint: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
        navController.navigate("RequestInfo/$name/$gender/$pickupPoint")
    },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Linha com a foto de perfil, nome e gênero
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Foto do perfil com a imagem do drawable
                Image(
                    painter = painterResource(id = R.drawable.profile),  // Usando imagem do drawable
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Nome e gênero
                Column {
                    Text(
                        text = name,
                        fontFamily = PoppinsFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = gender,
                        fontFamily = PoppinsFamily,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))



            // Ponto de Pick-up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pick-up Point:",
                    fontSize = 16.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Black
                )
                Text(text = pickupPoint, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botões para aceitar e recusar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDecline,
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Decline", color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Accept", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRequestForRide() {
    RequestForRide(navController = rememberNavController())
}
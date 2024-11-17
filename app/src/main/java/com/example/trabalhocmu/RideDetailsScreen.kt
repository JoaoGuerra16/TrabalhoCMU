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
import androidx.compose.ui.res.stringResource

@Composable
fun RideDetailsScreen(navController: NavController, from: String?, to: String?, date: String?) {
    SidebarScaffold(navController = navController) { paddingValues ->
        val scrollState = rememberScrollState()
        val askForRideText = stringResource(id = R.string.ask_for_ride)
        val orderSentText = stringResource(id = R.string.order_sent)

        var buttonTextState by remember { mutableStateOf(askForRideText) }
        var isButtonEnabled by remember { mutableStateOf(true) }
        var isRouteChecked by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.ride_details_title),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(id = R.string.profile_image_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))
                RatingStars(rating = 4)
                Spacer(modifier = Modifier.height(40.dp))

                UserInfoRow(label = stringResource(id = R.string.starting_point), info = from ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = stringResource(id = R.string.finish_point), info = to ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = stringResource(id = R.string.starting_date), info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = stringResource(id = R.string.expected_arrival), info = date ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = stringResource(id = R.string.available_places), info = "2")

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.pets_allowed), fontWeight = FontWeight.Bold)
                    Checkbox(checked = true, onCheckedChange = null)
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
                    Checkbox(checked = false, onCheckedChange = null)
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
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.need_for_pickup), fontWeight = FontWeight.Bold)
                    Checkbox(
                        checked = isRouteChecked,
                        onCheckedChange = { isRouteChecked = it }
                    )
                }

                // Exibe o mapa e as mensagens somente se a checkbox estiver marcada
                if (isRouteChecked) {
                    Text(
                        text = stringResource(id = R.string.pickup_location),
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.mapa),
                        contentDescription = stringResource(id = R.string.map_image_description),
                        modifier = Modifier
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup_location),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = stringResource(id = R.string.dropoff_location),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de "Ask for ride"
                Button(
                    onClick = {
                        buttonTextState = orderSentText
                        isButtonEnabled = false
                    },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF454B60)
                    ),
                    enabled = isButtonEnabled
                ) {
                    Text(text = buttonTextState)
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

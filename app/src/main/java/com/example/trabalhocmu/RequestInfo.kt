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
fun RequestInfo(navController: NavController, name: String, gender: String, pickupPoint: String) {
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

                UserInfoRow(label = "Username", info = name ?: "N/A")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Mobile", info = "912312312")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Email", info = "mariaguerra99@gmail.com")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Age", info = "20")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Gender", gender ?: "N/A")

                Spacer(modifier = Modifier.height(15.dp))


                Image(
                    painter = painterResource(id = R.drawable.mapa),
                    contentDescription = "Mapa",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.CenterHorizontally)  // Garante que o mapa está centralizado
                )

                UserInfoRow(label = "Pick up", info = "Felgueiras")
                Spacer(modifier = Modifier.height(15.dp))
                UserInfoRow(label = "Dropoff", info= "Lixa")

            }
        }

        // Composable para mostrar as informações do usuário com label e valor
        @Composable
        fun UserInfoRowRequestInfo(label: String, info: String) {
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
    }

}
    @Preview(showBackground = true)
    @Composable
    fun RequestforRideInfo() {
        RequestInfo(
            navController = rememberNavController(),
            name = "John Doe",
            gender = "Male",
            pickupPoint = "Felgueiras, rua do jardim"
        )
    }
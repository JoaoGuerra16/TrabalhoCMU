//package com.example.trabalhocmu.ui.screen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.trabalhocmu.R
//import com.example.trabalhocmu.ui.component.SidebarScaffold
//
//@Composable
//fun RequestInfo(navController: NavController, name: String, gender: String, pickupPoint: String) {
//    SidebarScaffold(navController = navController) { paddingValues ->
//        val currentLanguage = remember { mutableStateOf("PT") }
//        val scrollState = rememberScrollState()
//        var isButtonEnabled by remember { mutableStateOf(true) }
//        // Estado para controlar se o mapa e mensagens serão exibidos
//        var isRouteChecked by remember { mutableStateOf(false) }
//        var buttonState by remember { mutableStateOf("Waiting for confirmation") }
//        var buttonColor by remember { mutableStateOf(Color(0xFF454B60)) }
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(scrollState)
//                    .padding(25.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                Text(
//                    text = "Create Ride",
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight.Bold,
//
//                    )
//
//
//                UserInfoRow(label = "Username", info = name ?: "N/A")
//                Spacer(modifier = Modifier.height(15.dp))
//                UserInfoRow(label = "Mobile", info = "912312312")
//                Spacer(modifier = Modifier.height(15.dp))
//                UserInfoRow(label = "Email", info = "mariaguerra99@gmail.com")
//                Spacer(modifier = Modifier.height(15.dp))
//                UserInfoRow(label = "Age", info = "20")
//                Spacer(modifier = Modifier.height(15.dp))
//                UserInfoRow(label = "Gender", gender ?: "N/A")
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//
//                Image(
//                    painter = painterResource(id = R.drawable.mapa),
//                    contentDescription = "Mapa",
//                    modifier = Modifier
//                        .size(350.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//
//                UserInfoRow(label = "Pick up", info = "Felgueiras")
//                Spacer(modifier = Modifier.height(15.dp))
//                UserInfoRow(label = "Dropoff", info= "Lixa")
//
//            }
//        }
//
//
//        @Composable
//        fun UserInfoRowRequestInfo(label: String, info: String) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = "$label:", fontWeight = FontWeight.Bold)
//                Text(text = info)
//            }
//
//        }
//    }
//
//}
//    @Preview(showBackground = true)
//    @Composable
//    fun RequestforRideInfo() {
//        RequestInfo(
//            navController = rememberNavController(),
//            name = "John Doe",
//            gender = "Male",
//            pickupPoint = "Felgueiras, rua do jardim"
//        )
//    }
package com.example.trabalhocmu

import RatingViewModel
import android.os.Bundle
import android.provider.Telephony.Mms.Rate
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    companion object {
        val PoppinsFamily = FontFamily(
            Font(R.font.poppinsregular),  // Fonte Regular
            Font(R.font.poppinsbold)     // Fonte Bold
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    // Criar o estado do drawer (ModalNavigationDrawer)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val ratingViewModel: RatingViewModel = viewModel() // ViewModel compartilhado entre telas

    // Função para gerenciar o drawer com as telas que o utilizam
    @Composable
    fun DrawerWrapper(content: @Composable () -> Unit) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Sidebar(navController = navController, drawerState = drawerState)
            }
        ) {
            content()
        }
    }

    // Definir o comportamento de navegação para as telas
    NavHost(navController = navController, startDestination = "splash_screen") {
        // Tela de splash
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("RequestInfo/{name}/{gender}/{pickupPoint}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "N/A"
            val gender = backStackEntry.arguments?.getString("gender") ?: "N/A"
            val pickupPoint = backStackEntry.arguments?.getString("pickupPoint") ?: "N/A"
            RequestInfo(navController, name, gender, pickupPoint)
        }

        // Tela inicial (com Drawer)
        composable("StartingPage") {
            DrawerWrapper {
                StartingPage(navController = navController)
            }
        }

        // Tela de busca de caronas (com Drawer)
        composable("Find Rides") {
            DrawerWrapper {
                FindRides(navController = navController)
            }
        }

        // Telas de autenticação e registro
        composable("Register") {
            RegisterScreen(navController = navController)
        }
        composable("Login") {
            LoginScreen(navController = navController)
        }
        composable("ForgotPassword") {
            ForgotPassword(navController)
        }

        // Telas de perfil
        composable("EditProfile") {
            EditProfileScreen(navController)
        }
        composable("Profile") {
            Profile(navController, ratingViewModel) // Passando o ViewModel para Profile
        }

        // Tela de avaliação
        composable("Rate") {
            RateScreen(navController, ratingViewModel) // Passando o ViewModel para RateScreen
        }

        composable("RequestForRide") {
            RequestForRide(navController)
        }

        composable("My Rides") {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Sidebar(navController = navController, drawerState = drawerState)
                }
            ) {
                MyRides(navController = navController)
            }
        }


        // Tela de detalhes da carona
        composable("ride_details/{from}/{to}/{date}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val date = backStackEntry.arguments?.getString("date")
            RideDetailsScreen(navController, from, to, date)
        }
        composable("MyRidesGivingARide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: 0

            // Passando os parâmetros para a tela MyRidesGivingARide
            MyRidesGivingARide(
                navController = navController,
                from = from,
                to = to,
                startTime = startTime,
                arrivalTime = arrivalTime,
                date = date,
                availableSeats = availableSeats.toString()
            )
        }

        composable("MyRidesTakingARide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: 0

            // Passando os parâmetros para a tela MyRidesGivingARide
            MyRidesTakingARide(
                navController = navController,
                from = from,
                to = to,
                startTime = startTime,
                arrivalTime = arrivalTime,
                date = date,
                availableSeats = availableSeats.toString()
            )
        }


    }
}


@Composable
fun SplashScreen(navController: NavController) {
    // Estado da barra de progresso
    var progress by remember { mutableStateOf(0f) }

    // Lógica para aumentar o progresso da barra
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(20) // Ajuste a velocidade da barra
            progress += 0.02f // Incremento do progresso
        }
        navController.navigate("StartingPage") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Exibe o logo
            Image(
                painter = painterResource(id = R.drawable.logo), // Seu logotipo
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Barra de progresso
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .width(200.dp)
                    .height(8.dp),
                color = Color.Gray,
                trackColor = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}

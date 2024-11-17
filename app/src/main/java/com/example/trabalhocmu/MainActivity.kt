package com.example.trabalhocmu

import LanguageViewModel
import RatingViewModel
import android.os.Bundle
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
import androidx.compose.material3.DrawerState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar o idioma global
        setAppLanguage()

        setContent {
            AppNavigation()
        }
    }

    // Função para configurar o idioma global
    private fun setAppLanguage() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("selected_language", "English") ?: "English"
        val locale = when (savedLanguage) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en")
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val ratingViewModel: RatingViewModel = viewModel() // ViewModel compartilhado entre telas
    val languageViewModel: LanguageViewModel = viewModel(factory = LanguageViewModelFactory(LocalContext.current))

    // Criar o estado do drawer (ModalNavigationDrawer)
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Definir o comportamento de navegação para as telas
    NavHost(navController = navController, startDestination = "splash_screen") {
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
            DrawerWrapper(navController, drawerState) {
                StartingPage(navController)
            }
        }
        composable("Find Rides") {
            DrawerWrapper(navController, drawerState) {
                FindRides(navController)
            }
        }

        composable("Settings") {
            DrawerWrapper(navController, drawerState) {
                SettingsScreen(navController = navController, languageViewModel = languageViewModel)
            }
        }

        composable("Register") {
            RegisterScreen(navController = navController)
        }

        composable("Login") {
            LoginScreen(navController = navController)
        }

        composable("ForgotPassword") {
            ForgotPassword(navController)
        }

        composable("EditProfile") {
            EditProfileScreen(navController)
        }

        composable("Profile") {
            Profile(navController, ratingViewModel) // Passando o ViewModel para Profile
        }

        composable("Rate") {
            RateScreen(navController, ratingViewModel) // Passando o ViewModel para RateScreen
        }

        composable("RequestForRide") {
            RequestForRide(navController)
        }

        composable("RidesHistory") {
            RidesHistory(navController)
        }

        composable("My Rides") {
            DrawerWrapper(navController, drawerState) {
                MyRides(navController)
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

        composable("StartRide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: 0

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
        composable(
            "StartRide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}"
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats")

            StartRide(
                navController = navController,
                from = from,
                to = to,
                startTime = startTime,
                arrivalTime = arrivalTime,
                date = date,
                availableSeats = availableSeats ?: "0"
            )
        }

    }
}

@Composable
fun DrawerWrapper(navController: NavController, drawerState: DrawerState, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Sidebar(navController = navController, drawerState = drawerState)
        }
    ) {
        content()
    }
}

@Composable
fun SplashScreen(navController: NavController) {
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

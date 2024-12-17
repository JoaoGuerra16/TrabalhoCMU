package com.example.trabalhocmu.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabalhocmu.ui.component.Sidebar
import com.example.trabalhocmu.ui.component.SplashScreen
import com.example.trabalhocmu.ui.screen.*
import com.example.trabalhocmu.viewmodel.AuthViewModel
import com.example.trabalhocmu.viewmodel.AuthViewModelFactory
import com.example.trabalhocmu.viewmodel.LanguageViewModel
import com.example.trabalhocmu.viewmodel.RatingViewModel
import com.example.trabalhocmu.viewmodel.RideViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    ratingViewModel: RatingViewModel,
    languageViewModel: LanguageViewModel,
    rideViewModel: RideViewModel,
    lightValue: Float // Novo parâmetro do sensor de luz
) {
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("StartingPage") {
            DrawerWrapper(navController, drawerState) {
                StartingPage(navController)
            }
        }

        // Find Rides Screen
        composable("Find Rides") {
            DrawerWrapper(navController, drawerState) {
                FindRidesScreen(navController, rideViewModel)
            }
        }

        // Ride Details Screen
        composable("RideDetails/{rideId}") { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId")?.toIntOrNull()
                ?: return@composable
            RideDetailsScreen(navController, rideId, rideViewModel)
        }

        composable("Settings") {
            DrawerWrapper(navController, drawerState) {
                SettingsScreen(navController, languageViewModel)
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

        composable("Profile") {
            val authViewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(context = LocalContext.current)
            )
            Profile(navController = navController, authViewModel = authViewModel)
        }

        composable("Rate") {
            RateScreen(navController, ratingViewModel)
        }

        composable("RequestForRide") {
            RequestForRide(navController)
        }

        composable("RidesHistory") {
            RidesHistory(navController)
        }

        composable("Create Ride") {
            CreateRide(navController)
        }

        composable("My Rides") {
            DrawerWrapper(navController, drawerState) {
                MyRides(navController, rideViewModel)
            }
        }

        composable("EditProfile") {
            EditProfileScreen(navController)
        }

        composable("MyRidesGivingARide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: "0"
            MyRidesGivingARide(navController, from, to, startTime, arrivalTime, date, availableSeats)
        }

        // Tela para exibir o sensor de luz
        composable("LightSensorScreen") {
            LightSensorDisplay(lightValue = lightValue)
        }
    }
}

// Wrapper para adicionar o Sidebar aos screens
@Composable
fun DrawerWrapper(
    navController: NavHostController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
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
fun LightSensorDisplay(lightValue: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Current Light Level: ${lightValue.toInt()} lux",
            fontSize = 18.sp
        )
    }
}

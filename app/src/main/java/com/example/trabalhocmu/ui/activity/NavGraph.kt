package com.example.trabalhocmu.ui.activity


import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabalhocmu.ui.component.Sidebar
import com.example.trabalhocmu.ui.component.SplashScreen
import com.example.trabalhocmu.ui.screen.*
import com.example.trabalhocmu.viewmodel.LanguageViewModel
import com.example.trabalhocmu.viewmodel.RatingViewModel


@Composable
fun MainNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    ratingViewModel: RatingViewModel,
    languageViewModel: LanguageViewModel
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

        composable("Find Rides") {
            DrawerWrapper(navController, drawerState) {
                FindRides(navController)
            }
        }

        composable("Settings") {
            DrawerWrapper(navController, drawerState) {
                SettingsScreen(navController, languageViewModel)
            }
        }

        composable("Register") {
            RegisterScreen(navController=navController)
        }

        composable("Login") {

            LoginScreen( navController = navController)
        }

        composable("ForgotPassword") {
            ForgotPassword(navController)
        }

        composable("Profile") {
            Profile(navController, ratingViewModel)
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
            MyRides(navController)
        }

        // Tela de detalhes da carona
        composable("ride_details/{from}/{to}/{date}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val date = backStackEntry.arguments?.getString("date")
            RideDetailsScreen(navController, from, to, date)
        }

        composable("MyRidesTakingARide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val to = backStackEntry.arguments?.getString("to")
            val startTime = backStackEntry.arguments?.getString("startTime")
            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
            val date = backStackEntry.arguments?.getString("date")
            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: "0"
            MyRidesTakingARide(navController, from, to, startTime, arrivalTime, date, availableSeats)
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
    }
}

// Wrapper para adicionar o Sidebar ao redor de telas
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

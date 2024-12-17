package com.example.trabalhocmu.ui.activity


import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabalhocmu.room.entity.Ride
import com.example.trabalhocmu.ui.component.Sidebar
import com.example.trabalhocmu.ui.component.SplashScreen
import com.example.trabalhocmu.ui.screen.*
import com.example.trabalhocmu.viewmodel.AuthViewModel
import com.example.trabalhocmu.viewmodel.AuthViewModelFactory
import com.example.trabalhocmu.viewmodel.LanguageViewModel
import com.example.trabalhocmu.viewmodel.RatingViewModel
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.example.trabalhocmu.viewmodel.RideViewModelFactory


@Composable
fun MainNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    ratingViewModel: RatingViewModel,
    languageViewModel: LanguageViewModel,
    rideViewModel: RideViewModel,
    lightValue: Float
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
                FindRidesScreen(navController, rideViewModel) // Tela de rides disponíveis
            }
        }

        // Ride Details Screen
        composable("RideDetails/{rideId}") { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId")?.toIntOrNull()
                ?: return@composable
            RideDetailsScreen(navController, rideId, rideViewModel)
        }

        composable("RideRequestScreen/{rideId}") { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId")?.toInt() ?: 0
            RideRequestScreen(rideId = rideId, rideViewModel = rideViewModel, navController = navController)
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
            // Criar uma instância do ViewModel com o factory
            val authViewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(context = LocalContext.current)
            )
            val rideViewModel: RideViewModel = viewModel(
                factory = RideViewModelFactory(context = LocalContext.current)
            )

            // Passar o AuthViewModel para o ProfileScreen
            Profile(navController = navController, authViewModel = authViewModel, rideViewModel)
        }
        composable("RateScreen/{rideId}/{driverEmail}") { backStackEntry ->
            val context = LocalContext.current
            val rideId = backStackEntry.arguments?.getString("rideId")?.toInt() ?: 0
            val driverEmail = backStackEntry.arguments?.getString("driverEmail") ?: ""
            val viewModel: RideViewModel = viewModel(factory = RideViewModelFactory(context))
            RateScreen(navController, rideId, driverEmail, viewModel)
        }

        composable("RequestForRide") {
            RequestForRide(navController)
        }

        composable("RidesHistory") {
            val context = LocalContext.current
            val viewModel: RideViewModel = viewModel(factory = RideViewModelFactory(context))
            RidesHistory(navController, viewModel)
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
        composable("ManageRequests/{rideId}") { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId")?.toIntOrNull()
                ?: return@composable
            ManageRequestsScreen(navController, rideViewModel, rideId)
        }

        composable("ManagePassengers/{rideId}") { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId")?.toIntOrNull()
            if (rideId != null) {
                ManagePassengersScreen(rideId, rideViewModel, navController)
            }
        }



//        composable("MyRidesTakingARide/{from}/{to}/{startTime}/{arrivalTime}/{date}/{availableSeats}") { backStackEntry ->
//            val from = backStackEntry.arguments?.getString("from")
//            val to = backStackEntry.arguments?.getString("to")
//            val startTime = backStackEntry.arguments?.getString("startTime")
//            val arrivalTime = backStackEntry.arguments?.getString("arrivalTime")
//            val date = backStackEntry.arguments?.getString("date")
//            val availableSeats = backStackEntry.arguments?.getString("availableSeats") ?: "0"
//            MyRidesTakingARide(navController, from, to, startTime, arrivalTime, date, availableSeats)
//        }

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

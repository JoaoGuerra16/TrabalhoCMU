package com.example.trabalhocmu

import EditProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Defina os estados do perfil aqui
    val fullName = remember { mutableStateOf("John Doe") }
    val username = remember { mutableStateOf("JohnDoe") }
    val mobile = remember { mutableStateOf("+1234567890") }
    val email = remember { mutableStateOf("john.doe@example.com") }
    val age = remember { mutableStateOf("30") }
    val gender = remember { mutableStateOf("Male") }

    NavHost(navController = navController, startDestination = "StartingPage") {
        composable("StartingPage") {
            Profile(
                navController = navController,
                fullName = fullName,
                username = username,
                mobile = mobile,
                email = email,
                age = age,
                gender = gender
            )
        }
        composable("Login") {
            LoginScreen(navController)
        }
        composable("Register") {
            RegisterScreen(navController)
        }
        composable("edit_profile") {
            EditProfileScreen(
                navController = navController,
                fullName = fullName,
                username = username,
                mobile = mobile,
                email = email,
                age = age,
                gender = gender
            )
        }
    }
}

package com.example.trabalhocmu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable


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
    NavHost(navController = navController, startDestination = "StartingPage", builder =  {
        composable("StartingPage") {
            StartingPage(navController)
        }
        composable("Login") {
            LoginScreen(navController)
        }
        composable("Register") {
            RegisterScreen(navController)
        }
//        composable("ForgotPassword") {
//            ForgotPassword(navController)
//        }
    }
    )
}
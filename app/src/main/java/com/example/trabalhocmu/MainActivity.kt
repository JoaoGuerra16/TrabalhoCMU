package com.example.trabalhocmu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState

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
    // Criar o estado do drawer (ModalNavigationDrawer)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    // Usar o navController dentro da NavHost
    NavHost(navController = navController, startDestination = "StartingPage") {
        composable("StartingPage") {
            //  Sidebar será exibida
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Sidebar(navController = navController, drawerState = drawerState)
                }
            ) {
                StartingPage(navController = navController)
            }
        }
        composable("Find Rides") {
            //  Sidebar será exibida
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Sidebar(navController = navController, drawerState = drawerState)
                }
            ) {
                FindRides(navController = navController)

            }
        }


        composable("Register") {
            // Aqui, a Sidebar não será exibida
            RegisterScreen(navController = navController)
        }
        composable("Login") {
            // Aqui, a Sidebar não será exibida
            LoginScreen(navController = navController)
        }
    }
}


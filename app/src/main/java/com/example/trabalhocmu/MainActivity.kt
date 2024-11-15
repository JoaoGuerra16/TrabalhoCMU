package com.example.trabalhocmu


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import kotlinx.coroutines.delay

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

    // Definir o comportamento de navegação para as telas
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("StartingPage") {
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
            RegisterScreen(navController = navController)
        }
        composable("Login") {
            LoginScreen(navController = navController)
        }

        composable("edit_profile"){
            EditProfileScreen(navController )
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

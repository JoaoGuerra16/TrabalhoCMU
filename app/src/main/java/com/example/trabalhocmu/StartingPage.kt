package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartingPage(navController: NavController ) {
    val currentLanguage = remember { mutableStateOf("ENG") }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Coluna principal com os elementos do layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // App Name
            Text(
                text = "APP NAME",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(100.dp))

            // Login e Register
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Do you have an account?", color = Color(0xFF37474F), fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = {navController.navigate("Login")},
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF454B60)
                    )
                ) {
                    Text(
                        text = "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Or create one!", color = Color(0xFF37474F), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {navController.navigate("Register")},
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF454B60)
                    )
                ) {
                    Text(
                        text = "Register",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }

        }

        // Seletor de idioma no canto inferior esquerdo
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable {
                    currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (currentLanguage.value == "ENG") "PT | ENG" else "PT | ENG ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

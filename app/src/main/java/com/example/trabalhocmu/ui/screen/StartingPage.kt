package com.example.trabalhocmu.ui.screen

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
import com.example.trabalhocmu.ui.component.BackgroundWithImage
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.theme.PoppinsFamily


@Composable
fun StartingPage(navController: NavController) {
    val currentLanguage = remember { mutableStateOf("ENG") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundWithImage {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))


                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(110.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = if (currentLanguage.value == "PT") "NOME DO APP" else "APP NAME",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F),
                    fontFamily = PoppinsFamily
                )

                Spacer(modifier = Modifier.height(100.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (currentLanguage.value == "PT") "Você tem uma conta?" else "Do you have an account?",
                        color = Color(0xFF37474F),
                        fontSize = 20.sp,
                        fontFamily = PoppinsFamily
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { navController.navigate("Login") },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF454B60)
                        )
                    ) {
                        Text(
                            text = if (currentLanguage.value == "PT") "Entrar" else "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            fontFamily = PoppinsFamily // Aplica Poppins
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = if (currentLanguage.value == "PT") "Ou crie uma conta!" else "Or create one!",
                        color = Color(0xFF37474F),
                        fontSize = 20.sp,
                        fontFamily = PoppinsFamily
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { navController.navigate("Register") },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF454B60)
                        )
                    ) {
                        Text(
                            text = if (currentLanguage.value == "PT") "Cadastrar" else "Register",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                            fontFamily = PoppinsFamily // Aplica Poppins
                        )
                    }
                }
            }
        }


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
                text = if (currentLanguage.value == "ENG") "PT | ENG" else "ENG | PT ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily // Aplica Poppins
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartingPage() {
    StartingPage(navController = rememberNavController())
}

package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ForgotPassword(navController: NavController) {
    val scrollState = rememberScrollState()
    val currentLanguage = remember { mutableStateOf("PT") }

    // Criando variáveis de estado para os campos de texto
    val email = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Conteúdo principal da tela
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(110.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Forgot Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Reset using your email")

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "Email Address") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Coloca os botões nas extremidades
            ) {
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp), // Define altura uniforme
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF454B60)
                    ),
                    shape = RoundedCornerShape(12.dp) // Arredondamento uniforme
                ) {
                    Text(text = "Submit", color = Color.White)
                }

                Button(
                    onClick = { /* Colocar a logica para voltar para o login */ },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp) // Define altura uniforme
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    shape = RoundedCornerShape(12.dp) // Arredondamento uniforme
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFF000000)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Seletor de idioma no canto inferior esquerdo
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd) // Posiciona o Row no canto inferior esquerdo
                .padding(16.dp) // Adiciona padding opcional
                .clickable {
                    currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (currentLanguage.value == "PT") "PT | ENG" else "ENG | PT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


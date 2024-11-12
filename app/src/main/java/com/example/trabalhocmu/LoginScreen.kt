package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@Composable
fun LoginScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val currentLanguage = remember { mutableStateOf("PT") }

    // Criando variáveis de estado para os campos de texto
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Estado para controlar a visibilidade da senha
    val passwordVisible = remember { mutableStateOf(false) }

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

        Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Enter your username and password to login")

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(text = "Email Address") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value) Icons.Filled.VisibilityOff else  Icons.Filled.Visibility,
                        contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica para login */ },
            modifier = Modifier.width(175.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF454B60)
            )
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Don't you have an account? Register",
            color = Color.Blue, // Tornar o texto clicável mais visível
            modifier = Modifier.clickable {
                navController.navigate("Register")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                }
                .padding(top = 16.dp), // Opcional: ajuste de padding
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


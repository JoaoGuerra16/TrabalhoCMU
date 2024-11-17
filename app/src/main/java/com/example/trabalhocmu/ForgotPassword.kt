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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ForgotPassword(navController: NavController) {
    val scrollState = rememberScrollState()
    var currentLanguage by remember { mutableStateOf("PT") } // Estado para o idioma
    val email = remember { mutableStateOf("") }

    // Função para alterar o idioma
    val changeLanguage = {
        currentLanguage = if (currentLanguage == "PT") "ENG" else "PT"
    }

    // Strings que vão mudar dinamicamente com base no idioma
    val forgotPasswordText = if (currentLanguage == "PT") "Esqueceu a senha" else "Forgot Password"
    val resetUsingEmailText = if (currentLanguage == "PT") "Redefina usando seu e-mail" else "Reset using your email"
    val emailLabelText = if (currentLanguage == "PT") "Endereço de E-mail" else "Email Address"
    val submitText = if (currentLanguage == "PT") "Enviar" else "Submit"
    val cancelText = if (currentLanguage == "PT") "Cancelar" else "Cancel"
    val languageToggleText = if (currentLanguage == "PT") "PT | ENG" else "ENG | PT"

    // Usando o componente BackgroundWithImage
    Box(modifier = Modifier.fillMaxSize()) {
        // Componente com a imagem de fundo
        BackgroundWithImage {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(110.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = forgotPasswordText, // Usando a string com base no idioma
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = resetUsingEmailText, // Usando a string com base no idioma
                    fontFamily = PoppinsFamily
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = emailLabelText, fontFamily = PoppinsFamily) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* POR ENQUANTO NADA*/ },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = submitText, color = Color.White, fontFamily = PoppinsFamily)
                    }

                    Button(
                        onClick = { navController.navigate("Login") },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(50.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = cancelText,
                            color = Color(0xFFFFFFFF),
                            fontFamily = PoppinsFamily
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Seletor de idioma "PT | ENG" no canto inferior direito
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                modifier = Modifier
                    .clickable { changeLanguage() } // Altera o idioma ao clicar
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = languageToggleText, // Texto baseado no idioma selecionado
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = PoppinsFamily
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgotPassword() {
    ForgotPassword(navController = rememberNavController())
}

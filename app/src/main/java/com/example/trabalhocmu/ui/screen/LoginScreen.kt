package com.example.trabalhocmu.ui.screen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.component.BackgroundWithImage
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavController) {
    val scrollState = rememberScrollState()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val currentLanguage = remember { mutableStateOf("PT") }
    val loginError = remember { mutableStateOf(false) }

    if (loginError.value) {
        Toast.makeText(navController.context, "Erro no login, tente novamente", Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    text = if (currentLanguage.value == "PT") "Login" else "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (currentLanguage.value == "PT") "Entre com seu endereço email e senha" else "Enter your email and password",
                    fontFamily = PoppinsFamily
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = {
                        Text(
                            text = if (currentLanguage.value == "PT") "Endereço de e-mail" else "Email Address",
                            fontFamily = PoppinsFamily
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontFamily = PoppinsFamily)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = if (currentLanguage.value == "PT") "Senha" else "Password",
                            fontFamily = PoppinsFamily
                        )
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontFamily = PoppinsFamily)
                )

                Spacer(modifier = Modifier.height(5.dp))

                if (loginError.value) {
                    Text(
                        text = if (currentLanguage.value == "PT") "Email ou senha inválidos" else "Invalid email or password",
                        color = Color.Red,
                        fontFamily = PoppinsFamily,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Chama o ViewModel para autenticar o usuário
                        viewModel.loginUser(email.value, password.value) { isSuccess ->
                            if (isSuccess) {
                                navController.navigate("Profile") // Navega para a tela de perfil
                            } else {
                                loginError.value = true // Exibe erro caso o login falhe
                            }
                        }
                    },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
                ) {
                    Text(text = if (currentLanguage.value == "PT") "Entrar" else "Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (currentLanguage.value == "PT") "Não tem uma conta? Cadastre-se" else "Don't you have an account? Register",
                    modifier = Modifier.clickable {
                        navController.navigate("Register")
                    },
                    fontFamily = PoppinsFamily
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Seletor de idioma no canto inferior direito
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
                fontFamily = PoppinsFamily
            )
        }
    }
}


package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.theme.PoppinsFamily


@Composable
fun RegisterScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val currentLanguage = remember { mutableStateOf("PT") }

    // Criando variáveis de estado para os campos de texto
    val name = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val mobileNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    // Estado para controlar a visibilidade da senha
    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    BackgroundWithImage {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Adiciona rolagem ao formulário completo
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

            // Título de Registro
            Text(
                text = if (currentLanguage.value == "PT") "Cadastrar" else "Register",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Subtítulo
            Text(
                text = if (currentLanguage.value == "PT") "Preencha seus dados para cadastrar" else "Enter your details to register",
                fontFamily = PoppinsFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Nome
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Nome" else "Name", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Nome de usuário
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Nome de Usuário" else "Username", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Email
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Email" else "Email Address", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Idade
            OutlinedTextField(
                value = age.value,
                onValueChange = { age.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Idade" else "Age", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Gênero
            OutlinedTextField(
                value = gender.value,
                onValueChange = { gender.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Gênero" else "Gender", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Número de celular
            OutlinedTextField(
                value = mobileNumber.value,
                onValueChange = { mobileNumber.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Número de Celular" else "Mobile Number", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Senha com ícone para mostrar/ocultar
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Senha" else "Password", fontFamily = PoppinsFamily) },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    // Ícone de olho para mostrar/ocultar a senha
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Confirmar Senha com ícone para mostrar/ocultar
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Confirmar Senha" else "Confirm Password", fontFamily = PoppinsFamily) },
                visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    // Ícone de olho para mostrar/ocultar a senha
                    IconButton(onClick = { confirmPasswordVisible.value = !confirmPasswordVisible.value }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth() // Faz com que o campo ocupe toda a largura
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .width(175.dp)
                    .fillMaxWidth(), // Faz o botão ocupar toda a largura disponível
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF454B60)
                )
            ) {
                Text(text = if (currentLanguage.value == "PT") "Próximo" else "Next")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (currentLanguage.value == "PT") "Já tem uma conta? Faça login" else "Do you have an account? Login",
                modifier = Modifier.clickable {
                    navController.navigate("Login")
                }
            )

            // Seletor de idioma dentro do scroll, no canto inferior
            Row(
                modifier = Modifier
                    .align(Alignment.End) // Alinha o seletor de idioma no canto inferior direito
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
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}

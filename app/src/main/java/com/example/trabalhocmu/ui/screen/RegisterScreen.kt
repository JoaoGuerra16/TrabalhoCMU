package com.example.trabalhocmu.ui.screen

import AuthViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.room.entity.User
import com.example.trabalhocmu.ui.component.BackgroundWithImage
import com.example.trabalhocmu.ui.theme.PoppinsFamily


@Composable
fun RegisterScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Obtenha o ViewModel com Koin ou ViewModelProvider
    val authViewModel: AuthViewModel = viewModel()

    // UI para a tela de registro
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
                text = "Cadastrar",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Subtítulo
            Text(
                text = "Preencha seus dados para cadastrar",
                fontFamily = PoppinsFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campos de entrada (Nome, Username, Email, Idade, etc.)
            OutlinedTextField(
                value = authViewModel.name.value,
                onValueChange = { authViewModel.name.value = it },
                label = { Text(text = "Nome", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = authViewModel.username.value,
                onValueChange = { authViewModel.username.value = it },
                label = { Text(text = "Nome de Usuário", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = authViewModel.email.value,
                onValueChange = { authViewModel.email.value = it },
                label = { Text(text = "Email", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = authViewModel.age.value.toString(),
                onValueChange = { newValue -> authViewModel.age.value = newValue.toIntOrNull() ?: 0 },
                label = { Text(text = "Idade") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = authViewModel.gender.value,
                onValueChange = { authViewModel.gender.value = it },
                label = { Text(text = "Gênero", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = authViewModel.mobileNumber.value,
                onValueChange = { authViewModel.mobileNumber.value = it },
                label = { Text(text = "Número de Celular", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Senha
            OutlinedTextField(
                value = authViewModel.password.value,
                onValueChange = { authViewModel.password.value = it },
                label = { Text(text = "Senha", fontFamily = PoppinsFamily) },
                visualTransformation = if (authViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { authViewModel.passwordVisible.value = !authViewModel.passwordVisible.value }) {
                        Icon(
                            imageVector = if (authViewModel.passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (authViewModel.passwordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Confirmar Senha
            OutlinedTextField(
                value = authViewModel.confirmPassword.value,
                onValueChange = { authViewModel.confirmPassword.value = it },
                label = { Text(text = "Confirmar Senha", fontFamily = PoppinsFamily) },
                visualTransformation = if (authViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { authViewModel.confirmPasswordVisible.value = !authViewModel.confirmPasswordVisible.value }) {
                        Icon(
                            imageVector = if (authViewModel.confirmPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (authViewModel.confirmPasswordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { authViewModel.registerUser(context, navController) },
                modifier = Modifier.width(175.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
            ) {
                Text(text = "Próximo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Já tem uma conta? Faça login",
                modifier = Modifier.clickable {
                    navController.navigate("Login")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}

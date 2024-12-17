package com.example.trabalhocmu.ui.screen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.BackgroundWithImage
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.AuthViewModel
import com.example.trabalhocmu.viewmodel.AuthViewModelFactory
import com.example.trabalhocmu.viewmodel.RegisterState


@Composable
fun RegisterScreen(navController: NavController) {

    val context = LocalContext.current


    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )


    val name = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val mobileNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }


    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val currentLanguage = remember { mutableStateOf("PT") }
    val registerState by viewModel.registerState.collectAsState()

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Error -> {
                showToast((registerState as RegisterState.Error).message)
            }
            is RegisterState.Success -> {
                showToast("Registro realizado com sucesso!")
                navController.navigate("Login")
            }
            else -> {}
        }
    }

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

            // Título de Registo
            Text(
                text = if (currentLanguage.value == "PT") "Cadastrar" else "Register",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Subtítulo
            Text(
                text = if (currentLanguage.value == "PT") "Preencha os seus dados para cadastrar" else "Enter your details to register",
                fontFamily = PoppinsFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campos de Entrada
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Nome" else "Name", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Nome de Usuário" else "Username", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Email" else "Email Address", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = age.value,
                onValueChange = { age.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Idade" else "Age", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = gender.value,
                onValueChange = { gender.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Gênero" else "Gender", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = mobileNumber.value,
                onValueChange = { mobileNumber.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Número de Celular" else "Mobile Number", fontFamily = PoppinsFamily) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Senha" else "Password", fontFamily = PoppinsFamily) },
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
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text(text = if (currentLanguage.value == "PT") "Confirmar Senha" else "Confirm Password", fontFamily = PoppinsFamily) },
                visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible.value = !confirmPasswordVisible.value }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = if (currentLanguage.value == "PT") "Já tem uma conta? Faça login" else "Do you have an account? Login",
                modifier = Modifier.clickable {
                    navController.navigate("Login")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    if (password.value == confirmPassword.value) {

                        viewModel.registerUser(
                            name.value,
                            username.value,
                            email.value,
                            password.value,
                            confirmPassword.value,
                            age.value,
                            gender.value,
                            mobileNumber.value
                        )
                    } else {

                        showToast("As senhas não coincidem!")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
            ) {
                Text("Registar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))




    }
}



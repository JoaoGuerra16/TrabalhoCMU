package com.example.trabalhocmu.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabalhocmu.ui.component.BackgroundWithImage
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.AuthViewModel
import com.example.trabalhocmu.viewmodel.AuthViewModelFactory
import com.example.trabalhocmu.viewmodel.LoginState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)  // Usando a Factory personalizada
    )
    val currentLanguage = remember { mutableStateOf("PT") }

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    // Observando o estado do login
    val loginState by viewModel.loginState.collectAsState()

    val signInResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val account = task.result
        if (account != null) {
            viewModel.loginWithGoogle(account)
        } else {
            LoginState.Error("Falha ao autenticar com Google")
        }
    }

    // Função para iniciar o login com Google
    fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundWithImage {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    text = "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Entre com seu endereço de email e senha")

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = {
                        Text(
                            "Endereço de e-mail",
                            fontFamily = PoppinsFamily,
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Senha") },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible.value) "Mostrar senha" else "Esconder senha"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = if (currentLanguage.value == "PT") "Esqueceu a senha?" else "Forgot Password?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = PoppinsFamily,
                        modifier = Modifier.clickable {
                            navController.navigate("ForgotPassword")
                        }
                    )
                }
                // Exibir mensagens de erro ou sucesso
                LaunchedEffect(loginState) {
                    when (loginState) {
                        is LoginState.Success -> {
                            navController.navigate("Profile") {
                                popUpTo("Login") { inclusive = true }
                            }
                            viewModel.resetLoginState() // Reseta para evitar múltiplos disparos
                        }

                        is LoginState.Error -> {
                            // Exibe o erro como Toast
                            val errorMessage = (loginState as LoginState.Error).message
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }

                        else -> Unit
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.loginUser(email.value, password.value)
                    },
                    modifier = Modifier.width(175.dp)
                ) {
                    Text("Entrar")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (currentLanguage.value == "PT") "Não tem uma conta? Cadastre-se" else "Don't you have an account? Register",
                    modifier = Modifier.clickable {
                        navController.navigate("Register")
                    },
                    fontFamily = PoppinsFamily
                )
                Spacer(modifier = Modifier.height(25.dp))

                Text("Ou entre com Google")
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { signInWithGoogle() },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface, // Fundo branco para simular estilo Google
                        contentColor = MaterialTheme.colorScheme.onSurface // Texto e ícone em cor padrão
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp // Adiciona um leve efeito de elevação
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google logo",
                            tint = androidx.compose.ui.graphics.Color.Unspecified, // Mantém a cor original do ícone
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Entrar com Google",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

            }
        }
    }
    }
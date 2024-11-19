package com.example.trabalhocmu.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import com.example.trabalhocmu.viewmodel.AuthViewModel
import com.example.trabalhocmu.viewmodel.LoginState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@Composable
fun LoginScreen(viewModel: AuthViewModel = viewModel(), navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Observando o estado do login
    val loginState by viewModel.loginState.collectAsState()

    val signInResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val account = task.result
        if (account != null) {
            viewModel.loginWithGoogle(account)
        } else {
            viewModel.setLoginError("Falha ao autenticar com Google")
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
                    label = { Text("Endereço de e-mail") },
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

                Spacer(modifier = Modifier.height(25.dp))

                Text("Ou entre com Google")

                Button(
                    onClick = { signInWithGoogle() },
                    modifier = Modifier.width(175.dp)
                ) {
                    Text("Entrar com Google")
                }
            }
        }
    }
}

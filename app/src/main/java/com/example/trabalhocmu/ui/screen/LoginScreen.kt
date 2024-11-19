package com.example.trabalhocmu.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.trabalhocmu.room.entity.User
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt



@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavController) {
    val RC_SIGN_IN = 9001
    val scrollState = rememberScrollState()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val currentLanguage = remember { mutableStateOf("PT") }
    val loginError = remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Função para verificar se a senha fornecida corresponde ao hash
    fun checkPassword(inputPassword: String, storedHashedPassword: String): Boolean {
        return BCrypt.checkpw(inputPassword, storedHashedPassword)  // Verifica se a senha corresponde ao hash
    }

    // Função para buscar o usuário e verificar a senha
    fun loginUser(email: String, password: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    // Se não encontrar o usuário, exibe erro
                    loginError.value = true
                    return@addOnSuccessListener
                }

                val userDocument = result.documents.first()
                val storedHashedPassword = userDocument.getString("password") ?: ""

                // Verifica se a senha fornecida corresponde ao hash armazenado
                if (checkPassword(password, storedHashedPassword)) {
                    // Se a senha for correta, navega para a tela de perfil
                    navController.navigate("Profile")
                } else {
                    // Se a senha estiver incorreta, exibe erro
                    loginError.value = true
                }
            }
            .addOnFailureListener {
                // Exibe erro de falha ao buscar o usuário
                loginError.value = true
            }
    }

    // Função para autenticar com o Firebase usando o token do Google
    // Método para autenticar com o Google
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido, agora vamos armazenar os dados do usuário

                    // Armazena o usuário no banco de dados local (Room)
                    val user = User(
                        name = account.displayName ?: "Nome não disponível",
                        username = account.displayName ?: "Username não disponível",  // Pode ser o nome ou outra lógica
                        email = account.email ?: "Email não disponível",
                        age = 0,  // Idade será fornecida pelo usuário mais tarde
                        gender = "Indefinido",  // Gênero será fornecido pelo usuário mais tarde
                        mobileNumber = "Número não disponível",  // Caso tenha número
                        password = ""  // Senha não necessária
                    )

                    // Salvar no Room (isso pode ser feito em uma ViewModel ou diretamente na Activity)
                   // UserViewModel.cre(user) // Chama um método no ViewModel para inserir no banco de dados Room

                    // Navega para a tela de perfil ou outra tela após o login
                    navController.navigate("Profile")
                } else {
                    // Exibe erro se o login falhar
                    loginError.value = true
                }
            }
    }

    // Tratar o retorno do sign-in
    val signInResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result

            if (account != null) {
                firebaseAuthWithGoogle(account)
            } else {
                // Se não conseguir autenticar com o Google
                loginError.value = true
            }
        }
    // Função para iniciar o login com Google
    fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Adicione o ID do cliente
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)  // Aqui chamamos o launcher para iniciar o processo de autenticação
    }





    Box(modifier = Modifier.fillMaxSize()) {
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
                    // Exibe o texto de erro na interface
                    Text(
                        text = if (currentLanguage.value == "PT") "Email ou senha inválidos" else "Invalid email or password",
                        color = Color.Red,
                        fontFamily = PoppinsFamily,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)  // Adiciona algum espaço acima do texto
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para login com email e senha
                Button(
                    onClick = {
                        // Chama a função de login passando o email e a senha
                        loginUser(email.value, password.value)
                    },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF454B60))
                ) {
                    Text(text = if (currentLanguage.value == "PT") "Entrar" else "Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para login com Google
                Button(
                    onClick = { signInWithGoogle() },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
                ) {
                    Text(text = if (currentLanguage.value == "PT") "Entrar com Google" else "Sign in with Google")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navegação para a tela de registro
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
    }
}

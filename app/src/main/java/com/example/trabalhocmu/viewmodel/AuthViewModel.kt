package com.example.trabalhocmu.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhocmu.room.entity.User
import com.example.trabalhocmu.room.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(context: Context) : ViewModel() {
    private val authRepository = AuthRepository(context)

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    // Função para fazer login com email e senha
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val success = authRepository.loginUser(email, password)
            _loginState.value = if (success) {
                LoginState.Success
            } else {
                LoginState.Error("Email ou senha inválidos")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    // Função para login com Google
    fun loginWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val success = authRepository.loginWithGoogle(account)
            _loginState.value = if (success) {
                LoginState.Success
            } else {
                LoginState.Error("Falha na autenticação com Google")
            }
        }
    }

    // Função para verificar se o email já está registrado (Room)
    private suspend fun isEmailAlreadyRegistered(email: String): Boolean {
        val user = authRepository.getUserByEmail(email)
        return user != null
    }

    // Função para registrar o usuário
    // Função para registrar o usuário
    fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        age: String,
        gender: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            // Verificar se os campos estão preenchidos
            if (name.isBlank() || username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                _registerState.value = RegisterState.Error("Todos os campos devem ser preenchidos!")
                return@launch
            }

            // Verificar se as senhas coincidem
            if (password != confirmPassword) {
                _registerState.value = RegisterState.Error("As senhas não coincidem!")
                return@launch
            }

            // Verificar se o e-mail é válido
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _registerState.value = RegisterState.Error("Por favor, insira um e-mail válido.")
                return@launch
            }

            // Alterando o estado para Loading
            _registerState.value = RegisterState.Loading

            try {
                // Tenta criar o usuário no Firebase Authentication e no Room
                val authResult = authRepository.registerUser(
                    name = name,
                    username = username,
                    email = email,
                    password = password,
                    age = age,
                    gender = gender,
                    mobileNumber = mobileNumber
                )

                if (authResult) {
                    // Alterando o estado para Success
                    _registerState.value = RegisterState.Success
                } else {
                    // Caso a criação do usuário falhe
                    _registerState.value = RegisterState.Error("Erro ao criar conta. O e-mail pode já estar registrado.")
                }
            } catch (e: Exception) {
                // Erro inesperado
                _registerState.value = RegisterState.Error("Erro inesperado: ${e.message}")
            }
        }
    }


    // Função para salvar o usuário no Firestore
    private fun saveUserToFirestore(user: User) {
        val db = FirebaseFirestore.getInstance()

        // Crie uma referência à coleção de usuários
        val userRef = db.collection("users").document(user.email) // Usando o e-mail como ID

        // Salve os dados do usuário
        userRef.set(user)
            .addOnSuccessListener {
                // Sucesso no salvamento
                Log.d("Firestore", "Usuário salvo com sucesso!")
            }
            .addOnFailureListener { e ->
                // Erro ao salvar
                Log.w("Firestore", "Erro ao salvar usuário", e)
            }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

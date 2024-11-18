package com.example.trabalhocmu.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Função para realizar o login
    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult(true) // Sucesso no login
                        } else {
                            onResult(false) // Falha no login
                        }
                    }
            } catch (e: Exception) {
                // Em caso de erro na comunicação ou outro problema
                onResult(false)
            }
        }
    }
}

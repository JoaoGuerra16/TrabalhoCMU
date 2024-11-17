package com.example.trabalhocmu.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RatingViewModel : ViewModel() {
    // Lista de avaliações feitas pelas pessoas
    private val _ratings = mutableListOf<Int>()

    // MutableStateFlow para armazenar a média das avaliações
    private val _averageRating = MutableStateFlow(0f)
    val averageRating: StateFlow<Float> get() = _averageRating

    // Função para adicionar uma avaliação
    fun addRating(rating: Int) {
        _ratings.add(rating)
        calculateAverage()  // Recalcula a média sempre que uma nova avaliação for adicionada
    }

    // Função para calcular a média das avaliações
    private fun calculateAverage() {
        val sum = _ratings.sum()
        val average = if (_ratings.isNotEmpty()) sum.toFloat() / _ratings.size else 0f
        _averageRating.value = average
    }
}

package com.example.trabalhocmu

import LanguageViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context

class LanguageViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LanguageViewModel(context) as T
    }
}

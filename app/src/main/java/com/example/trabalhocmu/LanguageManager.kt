package com.example.trabalhocmu

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class LanguageManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    companion object {
        const val LANGUAGE_KEY = "selected_language"
    }

    // Função para obter o idioma salvo
    fun getSavedLanguage(): String {
        return sharedPreferences.getString(LANGUAGE_KEY, "English") ?: "English"
    }

    // Função para salvar o idioma escolhido
    fun saveLanguage(language: String) {
        sharedPreferences.edit().putString(LANGUAGE_KEY, language).apply()
    }

    // Função para aplicar a Locale globalmente
    fun applyLanguage(language: String, context: Context) {
        val locale = when (language) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en") // default to English
        }
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Salva a escolha do idioma
        saveLanguage(language)
    }
}

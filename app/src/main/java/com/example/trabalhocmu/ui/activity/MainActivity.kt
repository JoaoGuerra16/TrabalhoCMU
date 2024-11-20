package com.example.trabalhocmu.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.viewmodel.LanguageViewModel
import com.example.trabalhocmu.viewmodel.LanguageViewModelFactory
import com.example.trabalhocmu.viewmodel.RatingViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setAppLanguage()

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            // ViewModels compartilhados
            val ratingViewModel: RatingViewModel = viewModel()
            val languageViewModel: LanguageViewModel =
                viewModel(factory = LanguageViewModelFactory(this))

            // Navegação principal
            MainNavGraph(
                navController = navController,
                drawerState = drawerState,
                ratingViewModel = ratingViewModel,
                languageViewModel = languageViewModel
            )
        }
    }

    private fun setAppLanguage() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("selected_language", "English") ?: "English"
        val locale = when (savedLanguage) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en")
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

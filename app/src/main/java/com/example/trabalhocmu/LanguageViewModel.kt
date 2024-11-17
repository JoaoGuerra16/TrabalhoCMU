import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import java.util.*

class LanguageViewModel(private val context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val _selectedLanguage = mutableStateOf(getLanguageFromPrefs()) // Recupera o idioma armazenado

    val selectedLanguage: State<String> = _selectedLanguage

    // Função para mudar o idioma
    fun changeLanguage(language: String) {
        _selectedLanguage.value = language
        saveLanguageToPrefs(language) // Salva o idioma selecionado
        val locale = when (language) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en") // default to English
        }
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // Função para salvar o idioma nas SharedPreferences
    private fun saveLanguageToPrefs(language: String) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_language", language)
        editor.apply()
    }

    // Função para obter o idioma de SharedPreferences
    private fun getLanguageFromPrefs(): String {
        return sharedPreferences.getString("selected_language", "English") ?: "English" // Default é "English"
    }

    fun applySavedLanguage() {
        val savedLanguage = getLanguageFromPrefs() // Recupera o idioma salvo
        val locale = when (savedLanguage) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en")
        }
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}


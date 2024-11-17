package com.example.trabalhocmu
import LanguageViewModel
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import java.util.Locale





@Composable
fun SettingsScreen(navController: NavController, languageViewModel: LanguageViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val selectedLanguage = languageViewModel.selectedLanguage.value
    var isLanguageChanged by remember { mutableStateOf(false) } // Verifica se houve mudança
    val context = LocalContext.current

    SidebarScaffold(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Usando stringResource para o título da tela
            Text(
                text = stringResource(id = R.string.settings_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Seção para alteração de idioma
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Usando stringResource para o texto "Change Language"
                Text(
                    text = stringResource(id = R.string.change_language),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = { expanded.value = true },
                    modifier = Modifier
                        .width(150.dp)
                        .height(40.dp),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = selectedLanguage,
                        fontSize = 14.sp
                    )
                }
            }

            // Menu suspenso para selecionar o idioma
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                offset = DpOffset(x = 250.dp, y = 200.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.english)) },
                    onClick = {
                        languageViewModel.changeLanguage("English")
                        isLanguageChanged = true
                        expanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.portuguese)) },
                    onClick = {
                        languageViewModel.changeLanguage("Português")
                        isLanguageChanged = true
                        expanded.value = false
                    }
                )
            }

            // Se botão "Save Changes" for pressionado, as mudanças são aplicadas
            Spacer(modifier = Modifier.height(32.dp)) // Espaço entre as opções
            Button(
                onClick = {
                    if (isLanguageChanged) {
                        // Atualizando o idioma globalmente
                        val newLanguage = languageViewModel.selectedLanguage.value
                        val locale = when (newLanguage) {
                            "Português" -> Locale("pt", "PT")
                            "English" -> Locale("en", "US")
                            else -> Locale.getDefault()
                        }

                        // Aplicando as alterações no idioma
                        Locale.setDefault(locale)
                        val config = context.resources.configuration
                        config.setLocale(locale)
                        context.createConfigurationContext(config)

                        // Forçar recarregamento da tela (navegar de volta e recarregar)
                        navController.popBackStack() // Volta para a tela anterior
                        navController.navigate("settings") // Navega novamente para "Settings"
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp), // Definindo altura do botão
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Cor verde
            ) {
                // Usando stringResource para o texto do botão
                Text(
                    text = stringResource(id = R.string.save_changes),
                    color = Color.White, // Cor do texto dentro do botão
                    fontSize = 14.sp
                )
            }
        }
    }
}

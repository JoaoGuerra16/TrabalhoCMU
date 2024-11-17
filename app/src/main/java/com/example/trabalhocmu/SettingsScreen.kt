package com.example.trabalhocmu
import LanguageViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import java.util.Locale





@Composable
fun SettingsScreen(navController: NavController, languageViewModel: LanguageViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val selectedLanguage = languageViewModel.selectedLanguage.value
    var isLanguageChanged by remember { mutableStateOf(false) } // Verifica se houve mudança
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) } // Estado para o switch de notificações

    SidebarScaffold(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Título da tela
            Text(
                text = stringResource(id = R.string.settings_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Seção para habilitar/desabilitar notificações
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.notifications),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF4CAF50), // Verde quando ativado
                        uncheckedThumbColor = Color.Gray,     // Cinza quando desativado
                        checkedTrackColor = Color(0xFFC8E6C9), // Trilho verde claro quando ativado

                    ),


                )

            }

            // Seção para alteração de idioma
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

            // Botão "Save Changes"
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (isLanguageChanged) {
                        val newLanguage = languageViewModel.selectedLanguage.value
                        val locale = when (newLanguage) {
                            "Português" -> Locale("pt", "PT")
                            "English" -> Locale("en", "US")
                            else -> Locale.getDefault()
                        }
                        Locale.setDefault(locale)
                        val config = context.resources.configuration
                        config.setLocale(locale)
                        context.createConfigurationContext(config)
                        navController.popBackStack()
                        navController.navigate("settings")
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(
                    text = stringResource(id = R.string.save_changes),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

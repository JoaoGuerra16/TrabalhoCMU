package com.example.trabalhocmu
import LanguageViewModel
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(navController: NavController, languageViewModel: LanguageViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val selectedLanguage = languageViewModel.selectedLanguage.value

    SidebarScaffold(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Change Language",
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

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                offset = DpOffset(x = 250.dp, y = 200.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        languageViewModel.changeLanguage("English")
                        expanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Português") },
                    onClick = {
                        languageViewModel.changeLanguage("Português")
                        expanded.value = false
                    }
                )
            }
        }
    }
}


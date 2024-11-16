package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SidebarScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Sidebar(navController, drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo), // Substitua pelo seu recurso
                                contentDescription = "Logo",
                                modifier = Modifier.size(60.dp) // Ajuste o tamanho do logotipo
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Menu"
                            )
                        }
                    },
                    actions = {
                        // Espaço vazio para balancear o ícone de menu à esquerda
                        Spacer(modifier = Modifier.size(56.dp)) // Mesma largura do botão de menu
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                )
            },
            content = { padding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    // Imagem de fundo
                    Image(
                        painter = painterResource(id = R.drawable.background), // Substitua pela sua imagem
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Conteúdo principal, que ficará sobre a imagem
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        content(padding)  // O conteúdo da tela será passado aqui
                    }
                }
            }
        )
    }
}

@Composable
fun Sidebar(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val currentLanguage = remember { mutableStateOf("PT") }

    // Conteúdo do Drawer
    ModalDrawerSheet {
        // Ícone para fechar o menu
        IconButton(onClick = {
            scope.launch {
                drawerState.close()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Close Menu",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }

        Divider(color = Color.Gray)

        // Itens de navegação do Drawer
        NavigationDrawerItem(
            label = { Text("Profile") },
            selected = false,
            onClick = {
                navController.navigate("Profile")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("My rides") },
            selected = false,
            onClick = {
                navController.navigate("Rides")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Find rides") },
            selected = false,
            onClick = {
                navController.navigate("Find rides")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Ride's History") },
            selected = false,
            onClick = {
                navController.navigate("Ride's History")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = {
                navController.navigate("Settings")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f)) // Para empurrar os itens de navegação para cima

        // Logout e seletor de idioma na mesma linha no fundo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Alinha os itens à esquerda e direita
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logout Button
            Text(
                text = "Logout",
                color = Color.Red,
                modifier = Modifier.clickable {
                    navController.navigate("Login")
                }
            )

            // Seletor de idioma (PT|ENG)
            Text(
                text = if (currentLanguage.value == "PT") "PT | ENG" else "ENG | PT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    // Alterna o idioma
                    currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSidebarScaffold() {
    // Simula o NavController com um valor vazio (pode ser melhorado com um NavController real)
    SidebarScaffold(navController = rememberNavController()) { padding ->
        // Exemplo de conteúdo centralizado no Scaffold
    }
}
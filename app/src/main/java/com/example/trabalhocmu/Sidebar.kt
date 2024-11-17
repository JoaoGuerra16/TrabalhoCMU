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
import androidx.compose.ui.res.stringResource
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
                                contentDescription = stringResource(id = R.string.logo_description),
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
                                contentDescription = stringResource(id = R.string.open_menu)
                            )
                        }
                    },
                    actions = {
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
                        contentDescription = stringResource(id = R.string.background_description),
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
                contentDescription = stringResource(id = R.string.close_menu),
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }

        Divider(color = Color.Gray)

        // Itens de navegação do Drawer
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.profile)) },
            selected = false,
            onClick = {
                navController.navigate("Profile")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.my_rides)) },
            selected = false,
            onClick = {
                navController.navigate("My Rides")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.find_rides)) },
            selected = false,
            onClick = {
                navController.navigate("Find rides")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.ride_history)) },
            selected = false,
            onClick = {
                navController.navigate("Rate")
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(8.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.settings)) },
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
                text = stringResource(id = R.string.logout),
                color = Color.Red,
                modifier = Modifier.clickable {
                    navController.navigate("Login")
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
package com.example.trabalhocmu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SidebarScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Container principal que inclui o drawer e a top bar
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Sidebar(navController, drawerState)
        }
    ) {
        // Scaffold para TopBar e conteúdo da página
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Menu"
                            )
                        }
                    }
                )
            },
            content = content
        )
    }
}

@Composable
fun Sidebar(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    // Conteúdo do Drawer
    ModalDrawerSheet {
        // Icone para fechar o menu
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

        Spacer(modifier = Modifier.weight(1f))
    }
}

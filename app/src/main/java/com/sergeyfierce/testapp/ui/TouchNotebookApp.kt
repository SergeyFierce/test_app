package com.sergeyfierce.testapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergeyfierce.testapp.navigation.Screen
import com.sergeyfierce.testapp.ui.home.HomeScreen
import com.sergeyfierce.testapp.ui.contacts.ContactsListScreen
import com.sergeyfierce.testapp.ui.contacts.ContactAddScreen
import com.sergeyfierce.testapp.ui.contacts.ContactDetailsScreen
import com.sergeyfierce.testapp.ui.notes.NotesListScreen
import com.sergeyfierce.testapp.ui.notes.NoteAddScreen
import com.sergeyfierce.testapp.ui.notes.NoteDetailsScreen
import com.sergeyfierce.testapp.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun TouchNotebookApp() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Surface(
                        modifier = Modifier.size(72.dp),
                        shape = CircleShape
                    ) {
                        // Placeholder icon
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Touch NoteBook", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                    NavigationDrawerItem(
                        label = { Text("Главный экран") },
                        selected = false,
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Настройки") },
                        selected = false,
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Settings.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Поддержка") },
                        selected = false,
                        icon = { Icon(Icons.Default.SupportAgent, contentDescription = null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            // Mock support link
                            val telegramUri = Uri.parse("https://t.me/touchnotebook")
                            val intent = Intent(Intent.ACTION_VIEW, telegramUri)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { inner ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(inner)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(onCategory = { category ->
                        navController.navigate(Screen.ContactsList.createRoute(category.name))
                    }, onAddContact = {
                        navController.navigate(Screen.ContactAdd.createRoute())
                    }, openDrawer = { scope.launch { drawerState.open() } })
                }
                composable(Screen.ContactsList.route) { backStack ->
                    val category = backStack.arguments?.getString("category") ?: ""
                    ContactsListScreen(
                        category = category,
                        onAdd = { navController.navigate(Screen.ContactAdd.createRoute(category)) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.ContactAdd.route) { backStack ->
                    val category = backStack.arguments?.getString("category")
                    ContactAddScreen(
                        preselectedCategory = category,
                        onDone = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Контакт сохранён"
                                )
                            }
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.ContactDetails.route) {
                    ContactDetailsScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.NotesList.route) {
                    NotesListScreen(onBack = { navController.popBackStack() }, onAdd = {
                        navController.navigate(Screen.NoteAdd.route)
                    })
                }
                composable(Screen.NoteAdd.route) {
                    NoteAddScreen(onBack = { navController.popBackStack() }, onDone = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Заметка добавлена")
                        }
                        navController.popBackStack()
                    })
                }
                composable(Screen.NoteDetails.route) {
                    NoteDetailsScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

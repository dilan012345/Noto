package com.example.noto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noto.ui.theme.EditNoteScreen
import com.example.noto.ui.theme.HomeScreen
import com.example.noto.ui.theme.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                 HomeScreen(navController)
             }

                composable("settings") {
                    SettingsScreen(navController)
            }
                composable("edit note") {
                    val noteId = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<String>("noteId")
                    EditNoteScreen(navController, noteID = noteId)
                }

            }
    }
}



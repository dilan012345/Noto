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
import com.google.accompanist.navigation.animation.AnimatedNavHost

import androidx.compose.animation.*
import com.example.noto.ui.theme.ImportNotesScreen

@OptIn(ExperimentalAnimationApi::class)
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
                composable("import notes", enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }){
                    ImportNotesScreen(navController)
                }
                composable("settings", enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }) {
                    SettingsScreen(navController)
            }
                composable("edit note", enterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }) {
                    val noteId = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<String>("noteId")
                    EditNoteScreen(navController, noteID = noteId)
                }

            }
    }
}



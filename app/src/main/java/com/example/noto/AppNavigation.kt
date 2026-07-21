package com.example.noto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noto.ui.theme.EditNoteScreen
import com.example.noto.ui.theme.HomeScreen
import com.example.noto.ui.theme.SettingsScreen
import androidx.compose.runtime.remember

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import com.example.noto.ui.theme.AddNoteScreen
import com.example.noto.ui.theme.ImportNotesScreen


@Composable
fun AppNavigation(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable(
                "home",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ) {
                HomeScreen(navController = navController,
                    viewModel = viewModel)
            }
            composable(
                "import notes",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ){
                ImportNotesScreen(navController)
            }
            composable(
                "settings",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ) {
                SettingsScreen(navController)
            }
            composable(
                "edit note",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ) {

                val note = remember {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Note>("note")
                }
                EditNoteScreen(
                    navController = navController,
                    Note = note ?: Note(
                        Title = "",
                        Body = "",
                        Bookmarked = false
                    ),
                    viewModel = viewModel

                )
            }
            composable(
                "add note",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            ) {

                val note = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Note>("note")

                AddNoteScreen(
                    navController = navController,
                    Note = note ?: Note(
                        Title = "",
                        Body = "",
                        Bookmarked = false
                    ),
                    viewModel = viewModel
                )
            }

        }
    }
}
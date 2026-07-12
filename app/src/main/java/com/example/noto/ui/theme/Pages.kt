package com.example.noto.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.noto.BackButton
import com.example.noto.Background
import com.example.noto.BottomMenuForNotes
import com.example.noto.Bottommenu
import com.example.noto.Haze
import com.example.noto.ImportBox
import com.example.noto.NoteGrid
import com.example.noto.NoteScroll
import com.example.noto.SettingsGrid
import com.example.noto.Title
import com.example.noto.TitleSeperator

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Optional: no setContentView if no layout
    }
}
@Composable
fun HomeScreen(navController: NavController) {
    Background()
    NoteGrid(
        onNoteClick = {
                noteId ->
            // Get the current back stack entry safely
            val currentBackStackEntry = navController.currentBackStackEntry
            currentBackStackEntry?.savedStateHandle?.set("noteId", noteId)
            navController.navigate("edit note")
        }
    )
    Bottommenu (
        onSettingsClick = {
            navController.navigate("settings")
        }
        ,
        onImportClick = {
            navController.navigate("import notes")
        }
    )
}


@Composable
fun SettingsScreen(navController: NavController) {
    Background()

    SettingsGrid()
    Haze()
    BackButton(
        onBackClick = {
            navController.navigate("home")
        }
    )
}


@Composable
fun EditNoteScreen(navController: NavController, noteID: String?) {
    Background()
    NoteScroll()

    Haze()
    BottomMenuForNotes(
        onSettingsClick = { navController.navigate("settings") },
        onBackClick = { navController.navigate("home") }
    )

}

@Composable
fun ImportNotesScreen(navController: NavController) {
    Background()
    BackButton(
        onBackClick = {
            navController.navigate("home")
        }
    )
    Title(
        Titletext = "Import"
    )
    TitleSeperator()
    ImportBox()

}
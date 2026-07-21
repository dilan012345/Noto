package com.example.noto.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noto.BackButton
import com.example.noto.Background
import com.example.noto.BottomMenuForNotes
import com.example.noto.Bottommenu
import com.example.noto.Haze
import com.example.noto.ImportBox
import com.example.noto.Note
import com.example.noto.NoteGrid
import com.example.noto.NoteScroll
import com.example.noto.NoteViewModel
import com.example.noto.SettingsGrid
import com.example.noto.Title


class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Optional: no setContentView if no layout
    }
}
@Composable
fun HomeScreen(
               navController: NavController,
               viewModel: NoteViewModel
) {
    Background()
    val notes = viewModel.notes.collectAsState(initial = emptyList()).value
    NoteGrid(
        viewModel = viewModel,
        onNoteClick = { note ->

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("note", note)

            navController.navigate("edit note")
        }
    )
    Bottommenu (
        viewModel = viewModel,
        onAddClick = {viewModel.addNote(
            Note(
                Title = "Title",
                Body = "Body",
                Bookmarked = false
            )
        ) { note ->

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("note", note)

            navController.navigate("add note")
        }
             },
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
fun EditNoteScreen(navController: NavController,Note: Note, viewModel: NoteViewModel) {

    Background()
    NoteScroll(Title = Note.Title, Body = Note.Body, Id = Note.id, viewModel = viewModel)

    Haze()
    BottomMenuForNotes(
        onSettingsClick = { navController.navigate("settings") },
        onBackClick = { navController.navigate("home") }
    )

}

@Composable
fun ImportNotesScreen(navController: NavController) {
    Background()
    Haze()
    BottomMenuForNotes(

        onSettingsClick = { navController.navigate("settings") },
        onBackClick = { navController.navigate("home") }
    )
    Title(
        Titletext = "Import"
    )

    ImportBox()


}
@Composable
fun AddNoteScreen(navController: NavController,Note: Note,viewModel: NoteViewModel) {
    Background()
    NoteScroll(Title = "", Body = "\n".repeat(40), Id = Note.id, viewModel = viewModel)
    Haze()
    BottomMenuForNotes(
        onSettingsClick = { navController.navigate("settings") },
        onBackClick = { navController.navigate("home") }
    )

}

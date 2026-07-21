package com.example.noto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDAO
) : ViewModel() {

    val notes = dao.getNotes()

    fun addNote(note: Note, onDone: (Note) -> Unit) {
        viewModelScope.launch {
            val id = dao.insert(note)
            android.util.Log.d("ROOM", "Inserted $id")
            onDone(
                note.copy(id = id.toInt())
            )
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {

            dao.update(note)
            val rows = dao.update(note)
            android.util.Log.d("ROOM", "Saved Updated rows: $rows")

        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            dao.delete(note)
        }
    }

    fun updateTitle(id: Int, title: String) {
        viewModelScope.launch {
            dao.updateTitle(id, title)
        }
    }


}
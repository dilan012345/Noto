package com.example.noto

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDAO {

    // var: entity class

    @Query("SELECT * FROM Note")
    fun getNotes(): Flow<List<Note>>


    @Insert
    suspend fun insert(note: Note): Long


    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note):Int

    @Query("UPDATE Note SET Title = :title WHERE id = :id")
    suspend fun updateTitle(id: Int, title: String)
}
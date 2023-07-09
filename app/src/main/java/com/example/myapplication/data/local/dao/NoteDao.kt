package com.example.myapplication.data.local.dao

import androidx.room.*
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noteId DESC")
    fun getAllNotes() : MutableList<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE noteId like :id")
    suspend fun getNote(id : Int) : NoteEntity
}
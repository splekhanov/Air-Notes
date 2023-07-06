package com.example.myapplication.data

import androidx.room.*
import com.example.myapplication.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noteId DESC")
    fun getAllNotes() : MutableList<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE noteId like :id")
    fun getNote(id : Int) : NoteEntity
}
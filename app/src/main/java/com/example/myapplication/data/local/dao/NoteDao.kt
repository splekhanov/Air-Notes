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

    @Query("DELETE FROM $NOTE_TABLE WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY id DESC")
    fun getAllNotes(): MutableList<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id like :id")
    suspend fun getNote(id: Int): NoteEntity
}
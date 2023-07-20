package com.example.lemonnotes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lemonnotes.data.local.entities.NoteEntity
import com.example.lemonnotes.utils.Constants.NOTE_TABLE

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
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id like :id")
    suspend fun getNote(id: Int): NoteEntity
}
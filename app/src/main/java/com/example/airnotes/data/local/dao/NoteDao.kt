package com.example.airnotes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.airnotes.data.local.entities.NoteChecklist
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.data.local.entities.NoteType
import com.example.airnotes.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long {
        var noteId = 0L
        if (noteEntity.noteType == NoteType.TEXT) {
            noteId = insertNoteEntity(noteEntity)
        }
        return noteId
    }

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("DELETE FROM $NOTE_TABLE WHERE note_id = :id")
    suspend fun deleteNote(id: Long)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY note_id DESC")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE note_id like :id")
    suspend fun getNote(id: Long): NoteEntity

    // NOTE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteEntity(noteEntity: NoteEntity): Long

    @Query("DELETE FROM $NOTE_TABLE WHERE note_id = :id")
    suspend fun deleteNoteEntity(id: Long)

    // NOTE CHECKLIST
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklist(noteChecklist: NoteChecklist)
}
package com.example.airnotes.data.repo

import com.example.airnotes.data.local.dao.NoteDao
import com.example.airnotes.data.local.entities.NoteEntity
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    val getAllNotes = noteDao.getAllNotes()
    suspend fun addNote(note: NoteEntity) = noteDao.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)
    suspend fun deleteNote(id: Long) = noteDao.deleteNote(id)
    suspend fun deleteAllNotes() = noteDao.getAllNotes()
    suspend fun getNote(id: Long) = noteDao.getNote(id)

}
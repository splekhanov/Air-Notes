package com.example.lemonnotes.data.repo

import com.example.lemonnotes.data.local.dao.NoteDao
import com.example.lemonnotes.data.local.entities.NoteEntity
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    val getAllNotes = noteDao.getAllNotes()
    suspend fun addNote(note: NoteEntity) = noteDao.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)
    suspend fun deleteNote(id: Int) = noteDao.deleteNote(id)
    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()
    suspend fun getNote(id: Int) = noteDao.getNote(id)

}
package com.example.myapplication.data.repo

import com.example.myapplication.data.local.dao.NoteDao
import com.example.myapplication.data.local.entities.NoteEntity
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    suspend fun addNote(note: NoteEntity) = noteDao.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)
    suspend fun deleteNote(id: Int) = noteDao.deleteNote(id)
    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()
    suspend fun getNote(id: Int) = noteDao.getNote(id)
    fun getAllNotes() = noteDao.getAllNotes()

}
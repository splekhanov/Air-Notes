package com.example.myapplication.data.repo

import com.example.myapplication.data.local.dao.NoteDao
import com.example.myapplication.data.local.entities.NoteEntity
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    fun addNote(note: NoteEntity) = noteDao.insertNote(note)
    fun updateNote(note: NoteEntity) = noteDao.updateNote(note)
    fun deleteNote(note: NoteEntity) = noteDao.updateNote(note)
    fun getNote(id: Int) = noteDao.getNote(id)
    fun getAllNotes() = noteDao.getAllNotes()

}
package com.example.myapplication.data

import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)
    fun getNote() = noteDao.getNote()
    fun clearTable() = noteDao.clearTable()
}
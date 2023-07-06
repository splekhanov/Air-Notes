package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.NoteEntity
import com.example.myapplication.data.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val noteRepo: NoteRepo) : ViewModel() {

    suspend fun saveNote(note: NoteEntity) = noteRepo.insertNote(note)

    suspend fun getNote() = noteRepo.getNote().get(0).toString()

    fun getNames() = noteRepo.getNote().toString()

    fun clearTable() = noteRepo.clearTable()
}
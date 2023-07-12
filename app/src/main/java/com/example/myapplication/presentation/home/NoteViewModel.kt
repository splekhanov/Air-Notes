package com.example.myapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepo: NoteRepo) : ViewModel() {

    // save note
    fun addNote(taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = NoteEntity(
            noteTitle = taskName,
            noteDescription = taskDesc
        )
        notesRepo.addNote(notes)
    }

    // update note
    fun updateNotes(id: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = NoteEntity(
            id = id,
            noteTitle = taskName,
            noteDescription = taskDesc
        )
        notesRepo.updateNote(notes)
    }

    // delete note by ID
    fun deleteNoteByID(id: Int) = viewModelScope.launch {
        notesRepo.deleteNote(id)
    }
}
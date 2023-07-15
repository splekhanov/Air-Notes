package com.example.lemonnotes.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonnotes.data.local.entities.NoteEntity
import com.example.lemonnotes.data.repo.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepo: NoteRepo) : ViewModel() {

    val getAllData: LiveData<List<NoteEntity>> = notesRepo.getAllNotes

    // save note
    fun addNote(taskName: String, taskDesc: String) {
        viewModelScope.launch(IO) {
            val notes = NoteEntity(
                noteTitle = taskName,
                noteDescription = taskDesc
            )
            notesRepo.addNote(notes)
        }
    }

    // update note
    fun updateNotes(id: Int, taskName: String, taskDesc: String) {
        viewModelScope.launch(IO) {
            val notes = NoteEntity(
                id = id,
                noteTitle = taskName,
                noteDescription = taskDesc
            )
            notesRepo.updateNote(notes)
        }
    }

    // delete note by ID
    fun deleteNoteByID(id: Int) {
        viewModelScope.launch(IO) {
            notesRepo.deleteNote(id)
        }
    }
}
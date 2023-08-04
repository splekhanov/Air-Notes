package com.example.airnotes.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airnotes.data.local.entities.NoteChecklist
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.data.repo.NoteRepo
import com.example.airnotes.data.local.entities.NoteType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepo: NoteRepo) : ViewModel() {

    val getAllData: LiveData<List<NoteEntity>> = notesRepo.getAllNotes

    // save note
    fun addNote(noteTitle: String, noteType: NoteType, description: String, noteChecklist: List<NoteChecklist>, dateTime: LocalDateTime) {
        viewModelScope.launch(IO) {
            val note = NoteEntity(
                title = noteTitle,
                noteType = noteType,
                description = description,
                noteChecklist = noteChecklist,
                date = dateTime
            )
            notesRepo.addNote(note)
        }
    }

    // update note
    fun updateNotes(id: Long, noteTitle: String, description: String, noteType: NoteType, noteChecklist: List<NoteChecklist>, dateTime: LocalDateTime) {
        viewModelScope.launch(IO) {
            val note = NoteEntity(
                noteId = id,
                title = noteTitle,
                noteType = noteType,
                description = description,
                noteChecklist = noteChecklist,
                date = dateTime
            )
            notesRepo.updateNote(note)
        }
    }

    // delete note by ID
    fun deleteNoteByID(id: Long) {
        viewModelScope.launch(IO) {
            notesRepo.deleteNote(id)
        }
    }
}
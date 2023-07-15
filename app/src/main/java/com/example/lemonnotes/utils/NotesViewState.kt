package com.example.lemonnotes.utils

import com.example.lemonnotes.data.local.entities.NoteEntity

sealed class NotesViewState {
    object Empty : NotesViewState()
    object Loading : NotesViewState()
    data class Success(val notes: List<NoteEntity>) : NotesViewState()
    data class Error(val exception: Throwable) : NotesViewState()
}
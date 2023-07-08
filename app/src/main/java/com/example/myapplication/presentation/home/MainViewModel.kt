package com.example.myapplication.presentation.home

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repo.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(private val notesRepo: NoteRepo) : ViewModel() {


}
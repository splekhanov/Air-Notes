package com.example.airnotes.presentation.updateNote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.airnotes.R
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.databinding.FragmentUpdateNoteBinding
import com.example.airnotes.presentation.home.MainFragmentArgs
import com.example.airnotes.presentation.home.NoteViewModel
import com.example.airnotes.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private val viewModel: NoteViewModel by activityViewModels()
    lateinit var binding: FragmentUpdateNoteBinding
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val backToNotesListButton: FloatingActionButton = view.findViewById(R.id.backToNotesList)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)
        val editNoteDescription: EditText = view.findViewById(R.id.editDescription)

        saveNoteButton.isEnabled = false

        // receiving note args from navigation component
        val note = args.note
        val id = note.id

        // LISTENING TITLE EDIT TEXT
        editNoteTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(note.noteTitle != editNoteTitle.text.toString()) {
                    saveNoteButton.isEnabled = true
                } else if (note.noteTitle == editNoteTitle.text.toString()
                    && note.noteDescription == editNoteDescription.text.toString()) {
                    saveNoteButton.isEnabled = false
                }
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })

        // LISTENING DESCRIPTION EDIT TEXT
        editNoteDescription.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(note.noteDescription != editNoteDescription.text.toString()) {
                    saveNoteButton.isEnabled = true
                } else if (note.noteTitle == editNoteTitle.text.toString()
                    && note.noteDescription == editNoteDescription.text.toString()) {
                    saveNoteButton.isEnabled = false
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        with(binding) {
            noteLayout.editTitle.setText(note.noteTitle)
            noteLayout.editDescription.setText(note.noteDescription)

            backToNotesListButton.setOnClickListener {
                findNavController().navigate(R.id.action_updateNoteFragment_to_mainFragment)
            }

            saveNoteButton.setOnClickListener {
                var (title, desc, date) = getNoteContent(note)
                var updatedDate = LocalDateTime.now()

                //SET NEW DATETIME ONLY IF NOTE HAS BEEN CHANGED
                if(title.equals(note.noteTitle) && desc.equals(note.noteDescription)) {
                    date = note.date
                } else {
                    date = updatedDate
                }

                viewModel.updateNotes(id, title, desc, date).also {
                    requireActivity().toast(getString(R.string.saveNoteMsg))
                }
                findNavController().navigate(R.id.action_updateNoteFragment_to_mainFragment)
            }
        }
    }

    private fun getNoteContent(note: NoteEntity) = binding.noteLayout.let {
        Triple(
            it.editTitle.text.toString(),
            it.editDescription.text.toString(),
            note.date
        )
    }
}
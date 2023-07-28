package com.example.airnotes.presentation.addNote

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
import com.example.airnotes.R
import com.example.airnotes.databinding.FragmentAddNoteBinding
import com.example.airnotes.presentation.home.NoteViewModel
import com.example.airnotes.utils.Focus.openSoftKeyboard
import com.example.airnotes.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private val viewModel: NoteViewModel by activityViewModels()
    lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SAVE NOTE AND GO BACK TO THE MAIN SCREEN
        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val backToNotesListButton: FloatingActionButton = view.findViewById(R.id.backToNotesList)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)
        val editNoteDescription: EditText = view.findViewById(R.id.editDescription)

        openSoftKeyboard(editNoteTitle)

        saveNoteButton.isEnabled = false
        setupOnTextChangedListeners(editNoteTitle, editNoteDescription, saveNoteButton)

        // BACK TO NOTES LIST BUTTON
        backToNotesListButton.setOnClickListener {
            findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
        }

        // SAVE NOTE BUTTON
        saveNoteButton.setOnClickListener {
            val (title, note, timestamp) = getNoteContent()
            when {
                title.isEmpty() && note.isEmpty() -> {
                    findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
                }
                else -> {
                    viewModel.addNote(title, note, timestamp).also {
                        requireActivity().toast(getString(R.string.saveNoteMsg))
                    }
                    findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
                }
            }
        }
    }

    private fun getNoteContent() = binding.noteLayout.let {
        Triple(
            it.editTitle.text.toString(),
            it.editDescription.text.toString(),
            LocalDateTime.now()
        )
    }

    private fun setupOnTextChangedListeners(editNoteTitle : EditText,
                                            editNoteDescription : EditText,
                                            saveNoteButton : FloatingActionButton) {
        editNoteTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(editNoteTitle.text.isNotEmpty()) {
                    saveNoteButton.isEnabled = true
                } else if (editNoteTitle.text.isEmpty() && editNoteDescription.text.isEmpty()) {
                    saveNoteButton.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        editNoteDescription.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(editNoteDescription.text.isNotEmpty()) {
                    saveNoteButton.isEnabled = true
                } else if (editNoteDescription.text.isEmpty() && editNoteTitle.text.isEmpty()) {
                    saveNoteButton.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}
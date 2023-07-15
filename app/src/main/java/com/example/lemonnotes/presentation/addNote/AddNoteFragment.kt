package com.example.lemonnotes.presentation.addNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lemonnotes.R
import com.example.lemonnotes.databinding.FragmentAddNoteBinding
import com.example.lemonnotes.presentation.home.NoteViewModel
import com.example.lemonnotes.utils.Focus.openSoftKeyboard
import com.example.lemonnotes.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

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
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)

        openSoftKeyboard(editNoteTitle)

        saveNoteButton.setOnClickListener {
            val (title, note) = getNoteContent()
            when {
                title.isEmpty() && note.isEmpty() -> {
                    findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
                }
                else -> {
                    viewModel.addNote(title, note).also {
                        requireActivity().toast(getString(R.string.saveNoteMsg))
                    }
                    findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
                }
            }
        }
    }

    private fun getNoteContent() = binding.noteLayout.let {
        Pair(
            it.editTitle.text.toString(),
            it.editDescription.text.toString()
        )
    }
}
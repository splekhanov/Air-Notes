package com.example.myapplication.presentation.updateNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUpdateNoteBinding
import com.example.myapplication.presentation.home.MainFragmentArgs
import com.example.myapplication.presentation.home.NoteViewModel
import com.example.myapplication.utils.Focus
import com.example.myapplication.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private val viewModel: NoteViewModel by activityViewModels()
    lateinit var binding: FragmentUpdateNoteBinding
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SAVE NOTE AND GO BACK TO THE MAIN SCREEN
        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)

        Focus.openSoftKeyboard(editNoteTitle)

        // receiving bundles here
        val note = args.note
        val id = note!!.id

        with(binding) {
            noteLayout.editTitle.setText(note.noteTitle)
            noteLayout.editDescription.setText(note.noteDescription)

            saveNoteButton.setOnClickListener {

                val (title, note) = getNoteContent()

                // check whether both title & desc is not empty
                when {
                    title.isEmpty() -> {
                        requireActivity().toast(getString(R.string.saveEmptyNoteMsg))
                    }
                    note.isEmpty() -> {
                        requireActivity().toast(getString(R.string.saveEmptyNoteMsg))
                    }
                    else -> {
                        viewModel.updateNotes(id, title, note).also {
                            requireActivity().toast(getString(R.string.saveNoteMsg))
                        }
                        findNavController().navigate(R.id.action_updateNoteFragment_to_mainFragment)
                    }
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
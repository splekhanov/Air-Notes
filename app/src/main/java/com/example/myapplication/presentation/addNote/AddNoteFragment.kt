package com.example.myapplication.presentation.addNote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.presentation.home.NoteViewModel
import com.example.myapplication.utils.Focus.openSoftKeyboard
import com.example.myapplication.utils.toast
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
        Log.e("AAA", "Метод onCreateView класса AddNoteFragment")
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA", "Метод onViewCreated класса AddNoteFragment")

        // SAVE NOTE AND GO BACK TO THE MAIN SCREEN
        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)

        openSoftKeyboard(editNoteTitle)

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
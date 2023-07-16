package com.example.lemonnotes.presentation.updateNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lemonnotes.R
import com.example.lemonnotes.databinding.FragmentUpdateNoteBinding
import com.example.lemonnotes.presentation.home.MainFragmentArgs
import com.example.lemonnotes.presentation.home.NoteViewModel
import com.example.lemonnotes.utils.Focus
import com.example.lemonnotes.utils.toast
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
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)

        // receiving note args from navigation component
        val note = args.note
        val id = note!!.id

        with(binding) {
            noteLayout.editTitle.setText(note.noteTitle)
            noteLayout.editDescription.setText(note.noteDescription)

            saveNoteButton.setOnClickListener {
                val (title, note) = getNoteContent()
                viewModel.updateNotes(id, title, note).also {
                    requireActivity().toast(getString(R.string.saveNoteMsg))
                }
                findNavController().navigate(R.id.action_updateNoteFragment_to_mainFragment)
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
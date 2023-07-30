package com.example.airnotes.presentation.updateNote

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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

        val backToNotesListButton: FloatingActionButton = view.findViewById(R.id.backToNotesList)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)

        setTitleTextOptions(editNoteTitle)

        // receiving note args from navigation component
        val note = args.note

        with(binding) {
            noteLayout.editTitle.setText(note.noteTitle)
            noteLayout.editDescription.setText(note.noteDescription)

            backToNotesListButton.setOnClickListener {
                updateNote(note)
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

    private fun updateNote(note: NoteEntity) {
        var (title, desc, date) = getNoteContent(note)
        val updatedDate = LocalDateTime.now()

        //SET NEW DATETIME ONLY IF NOTE HAS BEEN CHANGED
        if(title != note.noteTitle || desc != note.noteDescription) {
            date = updatedDate
        }
        viewModel.updateNotes(note.id, title, desc, date).also {
            requireActivity().toast(getString(R.string.saveNoteMsg))
        }
        findNavController().navigate(R.id.action_updateNoteFragment_to_mainFragment)
    }

    // MAKE TITLE ACT AS MULTILINE BUT WITH ACTION NEXT ABILITY
    private fun setTitleTextOptions(title: EditText) {
        title.imeOptions = EditorInfo.IME_ACTION_NEXT
        title.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }
}
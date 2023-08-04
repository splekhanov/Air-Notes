package com.example.airnotes.presentation.addNote

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
import com.example.airnotes.R
import com.example.airnotes.databinding.FragmentAddNoteBinding
import com.example.airnotes.presentation.home.NoteViewModel
import com.example.airnotes.utils.Focus.openSoftKeyboard
import com.example.airnotes.data.local.entities.NoteType
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
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backToNotesListButton: FloatingActionButton = view.findViewById(R.id.backToNotesList)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)
        val editNoteDescription: EditText = view.findViewById(R.id.editDescription)

        setTitleTextOptions(editNoteTitle)
        openSoftKeyboard(editNoteTitle)

        // SAVE NOTE AND GO BACK TO THE MAIN SCREEN
        backToNotesListButton.setOnClickListener {
            val (title, description, timestamp) = getNoteContent()
            when {
                title.isEmpty() && description.isEmpty() -> {
                    findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
                }
                else -> {
                    viewModel.addNote(title, NoteType.TEXT, description, emptyList(), timestamp).also {
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

    // MAKE TITLE ACT AS MULTILINE BUT WITH ACTION NEXT ABILITY
    private fun setTitleTextOptions(title: EditText) {
        title.imeOptions = EditorInfo.IME_ACTION_NEXT
        title.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }
}